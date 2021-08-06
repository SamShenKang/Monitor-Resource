package com.tencent.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SysConfig {
    @Value("${ip}")
    private String ip;

    public String getIp() {
        return ip;
    }
}
