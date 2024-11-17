package com.shri.springai.services.impl;

import com.shri.springai.services.OpenAIService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class OpenAIServiceImpl implements OpenAIService {

    private final ChatClient.Builder chatClient;

    public OpenAIServiceImpl(ChatClient.Builder chatClient) {
        this.chatClient = chatClient;
    }

    @Override
    public String getAnswer(String question) {
//        PromptTemplate template = new PromptTemplate(question);
//        Prompt prompt = template.create();
        String content = chatClient.build().prompt().user(question).call().content();
        assert content != null;
        return content;
    }
}
