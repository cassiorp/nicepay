package com.cassio.nicepay.service;

import com.cassio.nicepay.client.authorize.AuthorizeClient;
import com.cassio.nicepay.client.authorize.dto.AuthorizeDTO;
import com.cassio.nicepay.exception.BusinessException;
import com.cassio.nicepay.exception.ForbiddenException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import feign.FeignException;

@Service
public class AuthorizeService {

  private static final Logger log = LoggerFactory.getLogger(TransferService.class);

  private final AuthorizeClient authorizeClient;

  public AuthorizeService(AuthorizeClient authorizeClient) {
    this.authorizeClient = authorizeClient;
  }

  public void authorize() {
    try {
      authorizeClient.authorize();
    }catch (FeignException e){
      if(e.status() == 403){
        throw new ForbiddenException();
      }else{
        throw new BusinessException();
      }
    }catch (Exception e){
        throw new BusinessException();
    }

  }

}
