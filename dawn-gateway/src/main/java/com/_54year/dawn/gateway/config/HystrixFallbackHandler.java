package com._54year.dawn.gateway.config;

import com._54year.dawn.core.enums.DawnBasicResultCode;
import com._54year.dawn.core.result.impl.DawnResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * Hystrix熔断 返回处理
 *
 * @author Andersen
 */
@Slf4j
@Component
public class HystrixFallbackHandler implements HandlerFunction<ServerResponse> {

	/**
	 * 熔断处理器
	 * @param serverRequest 请求
	 * @return 响应
	 */
	@Override
	public Mono<ServerResponse> handle(ServerRequest serverRequest) {
		serverRequest.attribute(ServerWebExchangeUtils.GATEWAY_ORIGINAL_REQUEST_URL_ATTR)
			.ifPresent(originalUrls -> log.error(">>>>>>>>>网关执行请求:{}失败,hystrix服务降级处理", originalUrls));
		return ServerResponse
			.status(HttpStatus.INTERNAL_SERVER_ERROR)
			.contentType(MediaType.APPLICATION_JSON)
			.body(BodyInserters.fromValue(DawnResult.failed(DawnBasicResultCode.SERVER_ERR)));
	}
}
