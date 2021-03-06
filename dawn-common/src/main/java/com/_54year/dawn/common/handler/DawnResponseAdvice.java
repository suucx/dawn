package com._54year.dawn.common.handler;

import com._54year.dawn.core.result.ResultReaderFactory;
import com._54year.dawn.common.annotation.DawnResult;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 封装返回结果（已弃用，留作了解）
 * RequestBodyAdvice可以在requestBody封装前修改 ResponseBodyAdvice在ResponseBody封装前进行修改
 * 实现ResponseBodyAdvice可以进行返回结果处理，及在HttpMessageConverter封装数据之前进行一些处理
 * 但是这儿的处理是已经选好了HttpMessageConverter，则不能修改返回数据类型
 * 这种方式不满足我们需求 我需要将Object封装成Map
 *
 * @author Andersen
 */
//@RestControllerAdvice
//@Order(2)
@Deprecated
public class DawnResponseAdvice implements ResponseBodyAdvice<Object> {
	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		if (returnType.hasMethodAnnotation(DawnResult.class)) {

			return true;
		}
		return false;
	}

	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
		response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
		selectedConverterType = MappingJackson2HttpMessageConverter.class;
		return ResultReaderFactory.getResultReader(body).load(body);
	}
}
