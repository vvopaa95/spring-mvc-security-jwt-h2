package com.example.mvctpl.security;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

@Configuration
@Slf4j
@EnableResourceServer
public class OAuth2ResourceServerConfig extends ResourceServerConfigurerAdapter {

  @Override
  public void configure(ResourceServerSecurityConfigurer config) {
    config.tokenServices(tokenServices());
  }

  @Bean
  public TokenStore tokenStore() {
    return new JwtTokenStore(accessTokenConverter());
  }

  @Bean
  public JwtAccessTokenConverter accessTokenConverter() {
    JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
    Resource resource = new ClassPathResource("public.txt");
    String publicKey = null;
    try {
      publicKey = IOUtils.toString(resource.getInputStream(), Charset.defaultCharset());
    } catch (final IOException e) {
      log.warn(e.getMessage());
    }
    converter.setVerifierKey(publicKey);
    return converter;
  }

  @Bean
  public DefaultTokenServices tokenServices() {
    DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
    defaultTokenServices.setTokenStore(tokenStore());
    return defaultTokenServices;
  }

  public Map<String, Object> getExtraInfo(OAuth2Authentication auth) {
    OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) auth.getDetails();
    OAuth2AccessToken accessToken = tokenStore().readAccessToken(details.getTokenValue());
    return accessToken.getAdditionalInformation();
  }
}
