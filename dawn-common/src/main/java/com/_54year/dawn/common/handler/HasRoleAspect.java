package com._54year.dawn.common.handler;


import com._54year.dawn.core.constant.BasicConstant;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com._54year.dawn.common.annotation.HasRole;
import com._54year.dawn.common.constant.CommonConstant;
import com._54year.dawn.common.exception.DawnNoPermissionException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 通过自定义角色注解进行角色认证
 *
 * @author Andersen
 */
@Aspect
@Order(99)
@Component
public class HasRoleAspect {
	/**
	 * 定义切入点 为注解
	 */
	@Pointcut("@annotation(com._54year.dawn.common.annotation.HasRole)")
	public void annotationPointcut() {

	}

	/**
	 * 在切点之前执行 一些业务
	 *
	 * @param joinPoint 切点
	 */
	@Before("annotationPointcut()&&@annotation(hasRole)")
	public void beforePointcut(JoinPoint joinPoint, HasRole hasRole) {
		// 此处进入到方法前  可以实现一些业务逻辑
		//获取request
		RequestAttributes ra = RequestContextHolder.getRequestAttributes();
		ServletRequestAttributes sra = (ServletRequestAttributes) ra;
		HttpServletRequest request = sra.getRequest();
		//获取请求头中的附的用户信息
		String extras = request.getHeader(CommonConstant.EXTRAS_HEADER_KEY);
		if (extras == null || extras.trim().isEmpty()) {
			throw new DawnNoPermissionException("你没有访问权限！");
		}
		//解析用户信息
		extras = new String(Base64Utils.decodeFromString(extras));
		JSONObject userJson = JSON.parseObject(extras);
		//如果用户信息中没有携带角色信息 则结束
		if (!userJson.containsKey(BasicConstant.ROLE_LIST_KEY)) {
			throw new DawnNoPermissionException("你没有访问权限！");
		}
		//获取用户当前角色列表
		List<String> roleList = userJson.getJSONArray(BasicConstant.ROLE_LIST_KEY).toJavaList(String.class);
		for(String role:roleList){
			//如果注解上角色标识=当前登录用户角色 || 当前用户角色为system后端服务 则放行
			if (hasRole.value().equals(role) || CommonConstant.DAWN_SERVER_ROLE_NAME.equals(role)) {
				return;
			}
		}
		throw new DawnNoPermissionException("你没有访问权限！");
	}

	/**
	 * 环绕通知
	 *
	 * @param joinPoint 切点
	 * @return
	 * @throws Throwable
	 */
	@Around("annotationPointcut()&&@annotation(hasRole)")
	public Object doAround(ProceedingJoinPoint joinPoint, HasRole hasRole) throws Throwable {
//		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
//		String[] params = methodSignature.getParameterNames();// 获取参数名称
//		Object[] args = joinPoint.getArgs();// 获取参数值
//		return joinPoint.proceed(args);
		return joinPoint.proceed();
	}

	/**
	 * 在切入点return内容之后切入内容（可以用来对处理返回值做一些加工处理）
	 *
	 * @param joinPoint 切点
	 */
	@AfterReturning("annotationPointcut()")
	public void doAfterReturning(JoinPoint joinPoint) {
	}


}
