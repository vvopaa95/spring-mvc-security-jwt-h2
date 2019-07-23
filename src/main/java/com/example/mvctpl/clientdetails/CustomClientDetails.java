package com.example.mvctpl.clientdetails;

import com.example.mvctpl.core.AuditEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
@EqualsAndHashCode(callSuper = false)
@Data
class CustomClientDetails extends AuditEntity {
  @NotNull
  private String clientId;
  private String clientSecret;
  @ElementCollection(targetClass=String.class)
  private Set<String> resourceIds = new HashSet<>();
  private boolean secretRequired;
  private boolean scoped;
  @ElementCollection(targetClass=String.class)
  private Set<String> scope = new HashSet<>();
  @ElementCollection(targetClass=String.class)
  private Set<String> authorizedGrantTypes = new HashSet<>();
  @ElementCollection(targetClass=String.class)
  private Set<String> registeredRedirectUri = new HashSet<>();
  @ElementCollection(targetClass=String.class)
  private Collection<String> authorities = new HashSet<>();
  private Integer accessTokenValiditySeconds;
  private Integer refreshTokenValiditySeconds;
  private boolean autoApprove;

  @Convert(converter = AdditionalInfoConverter.class)
  private Map<String, Object> additionalInformation = new HashMap<>();
}
