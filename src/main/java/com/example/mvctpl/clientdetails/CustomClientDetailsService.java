package com.example.mvctpl.clientdetails;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Primary
@Service
@Transactional
@AllArgsConstructor
public class CustomClientDetailsService implements ClientDetailsService {
  private static final String COMMA_DELIMITER = ",";
  private final CustomClientDetailsRepository clientDetailsRepository;

  @Override
  public ClientDetails loadClientByClientId(String clientId) {
    CustomClientDetails client = clientDetailsRepository.findByClientId(clientId);

    String resourceIds = String.join(COMMA_DELIMITER, client.getResourceIds());
    String scopes = String.join(COMMA_DELIMITER, client.getScope());
    String grantTypes = String.join(COMMA_DELIMITER, client.getAuthorizedGrantTypes());
    String authorities = String.join(COMMA_DELIMITER, client.getAuthorities());

    BaseClientDetails base = new BaseClientDetails(client.getClientId(), resourceIds, scopes, grantTypes, authorities);
    base.setClientSecret(client.getClientSecret());
    base.setAccessTokenValiditySeconds(client.getAccessTokenValiditySeconds());
    base.setRefreshTokenValiditySeconds(client.getRefreshTokenValiditySeconds());
    base.setAdditionalInformation(client.getAdditionalInformation());
    base.setAutoApproveScopes(client.getScope());
    return base;
  }
}
