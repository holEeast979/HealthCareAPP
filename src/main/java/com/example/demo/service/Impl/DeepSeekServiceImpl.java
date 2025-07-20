package com.example.demo.service.Impl;

        import com.example.demo.pojo.ai.ChatRequest;
        import com.example.demo.pojo.ai.ChatResponse;
        import com.example.demo.config.ChatUser;
        import com.example.demo.pojo.ai.Message;
        import com.example.demo.service.DeepSeekService;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.http.HttpHeaders;
        import org.springframework.http.HttpEntity;
        import org.springframework.http.HttpMethod;
        import org.springframework.http.ResponseEntity;
        import org.springframework.web.client.RestTemplate;
        import org.springframework.stereotype.Service;

        import java.util.ArrayList;
        import java.util.Arrays;
        import java.util.List;
        import java.util.regex.Matcher;
        import java.util.regex.Pattern;

@Service
public class DeepSeekServiceImpl implements DeepSeekService {

    @Autowired
    ChatUser chatUser;
    String url = "https://api.deepseek.com/chat/completions";
    String key = "sk-9bbb3d635a724f5c85ae14aa181b9602";

    public String call(String message) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Accept", "application/json");
        headers.add("Authorization", "Bearer " + key);

        ChatRequest request = new ChatRequest();
        request.setModel("deepseek-chat");
        Message msg = new Message("user", message);
        List<Message> list = new ArrayList<>();
        list.add(msg);
        request.setMessages(list);

        HttpEntity<ChatRequest> entity = new HttpEntity<>(request, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ChatResponse> response = restTemplate.exchange(url, HttpMethod.POST, entity, ChatResponse.class);

        // 获取原始回复内容
        String originalResponse = response.getBody().getChoices().get(0).getMessage().getContent();

        // 使用正则表达式去除多种特殊字符
        String processedResponse = removeSpecialCharacters(originalResponse);

        // 进一步简化回复内容
        String simplifiedResponse = simplifyResponse(processedResponse);

        return simplifiedResponse;
    }

    private String removeSpecialCharacters(String text) {
        // 正则表达式：匹配 #、*、- 等特殊字符，可按需添加其他特殊字符
        Pattern pattern = Pattern.compile("[#*'\'-]");
        Matcher matcher = pattern.matcher(text);
        return matcher.replaceAll("");
    }

    private String simplifyResponse(String response) {
        int indexOfOne = response.indexOf("。");
        if (indexOfOne != -1) {
            response = response.substring(0, indexOfOne);
        }

        // 设置字符长度限制，超过一定长度则截断
        int maxLength = 100; // 设定最大长度为100个字符，可根据需求调整
        if (response.length() > maxLength) {
            response = response.substring(0, maxLength) + "。";
        }

        // 去除括号内的举例或详细解释内容（简单示例）
        response = response.replaceAll("\\([^)]*#\\)", "");

        return response;
    }
}