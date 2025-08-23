package com.example.demo.pojo.ai;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

/**
 * 请求类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRequest {
    private String model;
    private List<Message> messages;
}
