package com.example.mvctpl.security;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.io.IOException;
import java.nio.charset.Charset;

@Configuration
@Slf4j
@EnableAuthorizationServer
public class OAuth2AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
  private final AuthenticationManager authenticationManager;
  private final ClientDetailsService clientDetailsService;
  private final String secretKey;

  @Autowired
  public OAuth2AuthorizationServerConfig(
    AuthenticationManager authenticationManager,
    ClientDetailsService clientDetailsService) throws IOException
  {
    this.authenticationManager = authenticationManager;
    this.clientDetailsService = clientDetailsService;
    Resource resource = new ClassPathResource("private.txt");
    secretKey = IOUtils.toString(resource.getInputStream(), Charset.defaultCharset());
  }

  @Bean
  public JwtAccessTokenConverter tokenEnhancer() {
    JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
    converter.setSigningKey(secretKey);
    return converter;
  }

  @Bean
  public TokenStore tokenStore() {
    return new JwtTokenStore(tokenEnhancer());
  }

  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
    endpoints
      .tokenStore(tokenStore())
      .accessTokenConverter(tokenEnhancer())
      .authenticationManager(authenticationManager);
  }

  @Override
  public void configure(AuthorizationServerSecurityConfigurer security) {
    security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
  }

  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    //clients.withClientDetails(clientDetailsService);
    String clientid = "vvopaa";
    String clientSecret = "{noop}123";
    clients.inMemory().withClient(clientid).secret(clientSecret).scopes("read", "write")
    .authorizedGrantTypes("password", "refresh_token").accessTokenValiditySeconds(20000)
    .refreshTokenValiditySeconds(20000);

  }
}
