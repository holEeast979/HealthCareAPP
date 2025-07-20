package com.example.demo.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

//有问题
@Component
@NoArgsConstructor
@AllArgsConstructor
@Data
@ConfigurationProperties("deeps-eek")
public class ChatUser {
    private String key;
    private String url;
}
