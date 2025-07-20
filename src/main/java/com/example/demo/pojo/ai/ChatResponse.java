package com.example.demo.pojo.ai;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChatResponse {
    private ArrayList<Choice> choices;
}
