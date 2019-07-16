package com.example.mvctpl.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.util.Arrays;

@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
  private final AuthenticationManager authenticationManager;
  private final CustomAccessTokenConverter customAccessTokenConverter;

  @Autowired
  public OAuth2AuthorizationServerConfig(
    @Qualifier("authenticationManagerBean") AuthenticationManager authenticationManager,
    CustomAccessTokenConverter customAccessTokenConverter
  ) {
    this.authenticationManager = authenticationManager;
    this.customAccessTokenConverter = customAccessTokenConverter;
  }

  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
    tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), accessTokenConverterAuthServer()));

    endpoints.tokenStore(tokenStoreAuthServer())
      .accessTokenConverter(accessTokenConverterAuthServer())
      .authenticationManager(authenticationManager);
  }

  @Bean
  public TokenStore tokenStoreAuthServer() {
    return new JwtTokenStore(accessTokenConverterAuthServer());
  }

  @Bean
  public JwtAccessTokenConverter accessTokenConverterAuthServer() {
    JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
    //converter.setAccessTokenConverter(customAccessTokenConverter);
    //converter.setSigningKey("123");

    KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(
      new ClassPathResource("vvopaa.jks"), "d4i6m8a7".toCharArray());
    converter.setKeyPair(keyStoreKeyFactory.getKeyPair("vvopaa"));
    return converter;
  }

  @Bean
  @Primary
  public DefaultTokenServices tokenServicesAuthServer() {
    DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
    defaultTokenServices.setTokenStore(tokenStoreAuthServer());
    defaultTokenServices.setSupportRefreshToken(true);
    return defaultTokenServices;
  }

  @Bean
  public TokenEnhancer tokenEnhancer() {
    return new CustomTokenEnhancer();
  }
}
