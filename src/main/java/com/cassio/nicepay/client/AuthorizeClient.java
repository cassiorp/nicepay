package com.cassio.nicepay.client;

import com.cassio.nicepay.client.config.FeignClientConfiguration;
import com.cassio.nicepay.client.dto.AuthorizeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "authorize",
    url = "${authorize.url}",
    configuration = FeignClientConfiguration.class
)
public interface AuthorizeClient {

  @GetMapping
  ResponseEntity<AuthorizeDTO> authorize();
}