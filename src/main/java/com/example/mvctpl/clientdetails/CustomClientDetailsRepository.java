package com.example.mvctpl.clientdetails;

import org.springframework.data.repository.CrudRepository;

public interface CustomClientDetailsRepository extends CrudRepository<CustomClientDetails, Long> {
  CustomClientDetails findByClientId(String clientId);
}
