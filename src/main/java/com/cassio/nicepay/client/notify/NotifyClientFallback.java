package com.cassio.nicepay.client.notify;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class NotifyClientFallback implements NotifyClient {
    private static final Logger log = LoggerFactory.getLogger(NotifyClientFallback.class);
    @Override
    public void notifyTransfer() {
        log.info("Transfer done, but notify was failed");
    }
}