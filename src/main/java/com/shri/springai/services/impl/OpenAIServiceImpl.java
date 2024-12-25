package com.shri.springai.services.impl;

import com.shri.springai.services.AIService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class OpenAIServiceImpl implements AIService {

    private final ChatClient.Builder chatClient;

    public OpenAIServiceImpl(ChatClient.Builder chatClient) {
        this.chatClient = chatClient;
    }

    @Override
    public String getAnswer(String question) {
//        PromptTemplate template = new PromptTemplate(question);
//        Prompt prompt = template.create();
        String content = chatClient.build().prompt().user(question).call().content();
        return content;
    }
}
