package com.cassio.nicepay.client.notify;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "notify",
    url = "${notify.url}",
    fallback = NotifyClientFallback.class
)
public interface NotifyClient {
  @PostMapping
  void notifyTransfer();
}
