package com.shri.springai.services.impl;

import com.shri.springai.records.Answer;
import com.shri.springai.records.GetCapitalRequest;
import com.shri.springai.records.Question;
import com.shri.springai.services.AIService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service(value = "mistralAIService")
public class MistralAIServiceImpl implements AIService {

    private final ChatClient.Builder chatClient;
    private final ChatModel chatModel;

    @Value("classpath:templates/get_capital_prompt.st")
    private Resource getCapitalPromptTemplate;


    public MistralAIServiceImpl(ChatClient.Builder chatClient, ChatModel chatModel) {
        this.chatClient = chatClient;
        this.chatModel = chatModel;
    }

    @Override
    public String getAnswer(String question) {
        return chatClient.build()
                .prompt()
                .user(question)
                .call().content();
    }

    @Override
    public String getAnswer(String question, String model) {
        PromptTemplate promptTemplate = new PromptTemplate(question);
        Prompt prompt = promptTemplate.create();
        ChatResponse call = chatModel.call(prompt);
        return call.getResult().getOutput().getContent();
    }

    @Override
    public Answer getAnswer(Question question) {
        PromptTemplate promptTemplate = new PromptTemplate(question.question());
        Prompt prompt = promptTemplate.create();
        ChatResponse call = chatModel.call(prompt);
        return new Answer(call.getResult().getOutput().getContent());
    }

    @Override
    public Answer getCapital(GetCapitalRequest getCapitalRequest) {
        PromptTemplate promptTemplate = new PromptTemplate(getCapitalPromptTemplate);
        Prompt prompt = promptTemplate.create(Map.of("stateOrCountry", getCapitalRequest.stateOrCountry()));
        ChatResponse call = chatModel.call(prompt);
        return new Answer(call.getResult().getOutput().getContent());
    }
}
