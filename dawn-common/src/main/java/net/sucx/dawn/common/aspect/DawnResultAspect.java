package net.sucx.dawn.common.aspect;

import net.sucx.dawn.basic.constant.DawnResultMap;
import net.sucx.dawn.basic.util.CheckEmptyUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 通过自定义注解进行结果封装
 *
 * @author Andersen
 */
@Aspect
@Order(1)
@Component
public class DawnResultAspect {
	/**
	 * 定义切入点 为注解
	 */
	@Pointcut("@annotation(net.sucx.dawn.common.annotation.DawnResult)")
	public void annotationPointcut() {

	}


	/**
	 * 环绕通知
	 *
	 * @param joinPoint 切点
	 * @return
	 * @throws Throwable
	 */
	@Around("annotationPointcut()")
	public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
//		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
//		String[] params = methodSignature.getParameterNames();// 获取参数名称
		Object[] args = joinPoint.getArgs();// 获取参数值
		Object result = joinPoint.proceed(args);
		if (result instanceof Boolean) {
			result = (Boolean) result ? DawnResultMap.success() : DawnResultMap.fail("操作失败");
		} else if (result instanceof String) {
			result = CheckEmptyUtils.stringIsEmpty((String) result) ? DawnResultMap.fail() : DawnResultMap.success(result);
		} else if (result instanceof Map) {
			result = CheckEmptyUtils.mapIsEmpty((Map) result) ? DawnResultMap.fail("没有更多数据了") : DawnResultMap.success(result);
		} else if (result instanceof List) {
			result = CheckEmptyUtils.listIsEmpty((List) result) ? DawnResultMap.fail("没有更多数据了") : DawnResultMap.success(result);
		} else {
			result = result == null ? DawnResultMap.fail() : DawnResultMap.success(result);
		}
		return result;
	}


}