package com.shri.springai.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shri.springai.records.*;
import com.shri.springai.services.AIService;
import org.slf4j.Logger;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service(value = "mistralAIService")
public class MistralAIServiceImpl implements AIService {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(MistralAIServiceImpl.class);

    private final ChatClient.Builder chatClient;
    private final ChatModel chatModel;
    private final ObjectMapper objectMapper;

    @Value("classpath:templates/get_capital_prompt.st")
    private Resource getCapitalPromptTemplate;

    @Value(value = "classpath:templates/get_capital_info_prompt.st")
    private Resource getGetCapitalInfoPromptTemplate;

    @Value(value = "classpath:templates/get_capital_prompt_format.st")
    private Resource getCapitalPromptFormatTemplate;



    public MistralAIServiceImpl(ChatClient.Builder chatClient, ChatModel chatModel, ObjectMapper objectMapper) {
        this.chatClient = chatClient;
        this.chatModel = chatModel;
        this.objectMapper = objectMapper;
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
        ChatResponse response = chatModel.call(prompt);
        String content = response.getResult().getOutput().getContent();
        log.info("Response: {}", content);
        String responseAsString;
        try {
            JsonNode jsonNode = objectMapper.readTree(content);
            responseAsString = jsonNode.get("answer").asText();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return new Answer(responseAsString);
    }

    @Override
    public Answer getCapitalInfo(GetCapitalRequest getCapitalRequest) {
        PromptTemplate promptTemplate = new PromptTemplate(getGetCapitalInfoPromptTemplate);
        Prompt prompt = promptTemplate.create(Map.of("stateOrCountry", getCapitalRequest.stateOrCountry()));
        ChatResponse call = chatModel.call(prompt);
        return new Answer(call.getResult().getOutput().getContent());
    }

    @Override
    public GetCapitalInfoResponse getCapitalInfoFormatBinding(GetCapitalRequest getCapitalRequest) {
        BeanOutputConverter<GetCapitalInfoResponse> converter = new BeanOutputConverter<>(GetCapitalInfoResponse.class);
        String format = converter.getFormat();
        log.info("\nFormat: {}", format);

        PromptTemplate promptTemplate = new PromptTemplate(getCapitalPromptFormatTemplate);
        Prompt prompt = promptTemplate.create(Map.of("stateOrCountry", getCapitalRequest.stateOrCountry(), "format", format));
        ChatResponse call = chatModel.call(prompt);
        return converter.convert(call.getResult().getOutput().getContent());
    }

    @Override
    public GetCapitalResponse getCapitalJsonFormat(GetCapitalRequest getCapitalRequest) {
        BeanOutputConverter<GetCapitalResponse> converter = new BeanOutputConverter<>(GetCapitalResponse.class);
        String format = converter.getFormat();
        log.info("Format: {}", format);

        PromptTemplate promptTemplate = new PromptTemplate(getCapitalPromptFormatTemplate);
        Prompt prompt = promptTemplate.create(Map.of("stateOrCountry", getCapitalRequest.stateOrCountry(), "format", format));
        ChatResponse response = chatModel.call(prompt);

        return converter.convert(response.getResult().getOutput().getContent());
    }

}
