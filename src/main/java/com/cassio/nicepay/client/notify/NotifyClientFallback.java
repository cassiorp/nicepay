package com.cassio.nicepay.client.notify;

import java.util.logging.Logger;
import org.springframework.stereotype.Component;

@Component
public class NotifyClientFallback implements NotifyClient {
    public static final Logger log = Logger.getLogger(NotifyClient.class.getName());
    @Override
    public void notifyTransfer() {
        log.info("Transfer done, but notify was failed");
    }
}