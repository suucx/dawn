package com._54year.dawn.auth.config;

import com._54year.dawn.auth.entity.DawnUser;
import com._54year.dawn.core.constant.BasicConstant;
import lombok.SneakyThrows;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * JwtToken 负载数据增强
 *
 * @author Andersen
 */
public class DawnTokenEnhancer implements TokenEnhancer {

	/**
	 * 增强方法
	 *
	 * @param accessToken    token
	 * @param authentication 授权信息
	 * @return 增强后的token
	 */
	@SneakyThrows
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		Map<String, Object> info = new HashMap<>();
		//从authentication中获取当前登录用户信息
		DawnUser user = (DawnUser) authentication.getUserAuthentication().getPrincipal();
//		for (Field field : DawnUser.class.getDeclaredFields()) {
//			field.setAccessible(true);
//			info.put(field.getName(), field.get(user) != null ? field.get(user).toString() : "");
//		}
		info.put("user_id", user.getUserId());
		info.put("nick_name",user.getNickName());
		info.put(BasicConstant.ROLE_LIST_KEY,user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
		//设置附加信息
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
		return accessToken;
	}
}
