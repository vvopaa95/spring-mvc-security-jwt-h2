package com.example.mvctpl.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
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

  private String clientid = "tutorialspoint";
  private String clientSecret = "my-secret-key";
  private String privateKey = "private key";
  private String publicKey = "public key";

  @Autowired
  public OAuth2AuthorizationServerConfig(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }

  @Bean
  public JwtAccessTokenConverter tokenEnhancer() {
    JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
    converter.setSigningKey(privateKey);
    converter.setVerifierKey(publicKey);
    return converter;
  }

  @Bean
  public TokenStore tokenStore() {
    return new JwtTokenStore(tokenEnhancer());
  }

  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
    //TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
    //tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), accessTokenConverter()));

    endpoints.tokenStore(tokenStore())
      .accessTokenConverter(tokenEnhancer())
      .authenticationManager(authenticationManager);
  }

  @Override
  public void configure(AuthorizationServerSecurityConfigurer security) {
    security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
  }

//    @Bean
//    public JwtAccessTokenConverter accessTokenConverter() {
//      JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
//      //converter.setAccessTokenConverter(customAccessTokenConverter);
//      //converter.setSigningKey("123");
//
//      KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(
//        new ClassPathResource("vvopaa.jks"), "d4i6m8a7".toCharArray());
//      converter.setKeyPair(keyStoreKeyFactory.getKeyPair("vvopaa"));
//      return converter;
//    }
//
//    @Bean
//    @Primary
//    public DefaultTokenServices tokenServices() {
//      DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
//      defaultTokenServices.setTokenStore(tokenStore());
//      defaultTokenServices.setSupportRefreshToken(true);
//      return defaultTokenServices;
//    }

  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    clients.inMemory().withClient(clientid).secret(clientSecret).scopes("read", "write")
      .authorizedGrantTypes("password", "refresh_token").accessTokenValiditySeconds(20000)
      .refreshTokenValiditySeconds(20000);

  }
}
