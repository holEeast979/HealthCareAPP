package com.example.demo.model;

//短信验证
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties("smsmodel")
public class SMSModel {
    private String accountId;

    private String authToken;

    private String appId;
}
