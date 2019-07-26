package com.example.mvctpl.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.Charset;

@Configuration
@Slf4j
@RequiredArgsConstructor
@EnableAuthorizationServer
public class OAuth2AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
  private final AuthenticationManager authenticationManager;
  private final ClientDetailsService clientDetailsService;
  private final PasswordEncoder passwordEncoder;
  private String secretKey;

  @PostConstruct
  public void initialize() throws IOException {
    Resource resource = new ClassPathResource("private.txt");
    secretKey = IOUtils.toString(resource.getInputStream(), Charset.defaultCharset());
  }

  @Bean
  public JwtAccessTokenConverter tokenConverter() {
    JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
    converter.setSigningKey(secretKey);
    return converter;
  }

  @Bean
  public TokenStore tokenStore() {
    return new JwtTokenStore(tokenConverter());
  }

  @Bean
  public TokenEnhancer tokenEnhancer() {
    return new CustomTokenEnhancer();
  }

  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
    endpoints.tokenStore(tokenStore())
       .accessTokenConverter(tokenConverter())
       .tokenEnhancer(tokenEnhancer())
       .authenticationManager(authenticationManager);
  }

  @Override
  public void configure(AuthorizationServerSecurityConfigurer security) {
    security.tokenKeyAccess("permitAll()")
       .checkTokenAccess("isAuthenticated()")
       .passwordEncoder(passwordEncoder);
  }

  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    clients.withClientDetails(clientDetailsService);
  }
}
