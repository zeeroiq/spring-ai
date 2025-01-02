package com.shri.springai;

import org.springframework.ai.autoconfigure.mistralai.MistralAiChatProperties;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.mistralai.MistralAiChatModel;
import org.springframework.ai.mistralai.MistralAiChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
public class BaseTest {

    @Autowired
    public MistralAiChatModel chatModel;
    @Autowired
    public MistralAiChatProperties chatProperties;

    public String chat(String prompt) {
        PromptTemplate promptTemplate = new PromptTemplate(prompt);
        Prompt promptToExecute = promptTemplate.create();

        return chatModel.call(promptToExecute).getResult().getOutput().getContent();
    }

    public String chat(String prompt, Map<String, Object> paramToBind) {
        PromptTemplate promptTemplate = new PromptTemplate(prompt, paramToBind);
        Prompt promptToExecute = promptTemplate.create();

        return chatModel.call(promptToExecute).getResult().getOutput().getContent();
    }

    public String chat(String prompt, float temperature, String model, Map<String, Object> paramToBind) {
//        MistralAiChatOptions chatOptions = MistralAiChatOptions.fromOptions(chatProperties.getOptions());
//        chatOptions.setTemperature(temperature);
//        chatOptions.setModel(model);
        MistralAiChatOptions chatOptions = new MistralAiChatOptions.Builder()
                .withTemperature(temperature)
                .withModel(model)
                .build();
        PromptTemplate promptTemplate = new PromptTemplate(prompt, paramToBind);
        Prompt promptToExecute = new Prompt(promptTemplate.createMessage(), chatOptions);

        return chatModel.call(promptToExecute).getResult().getOutput().getContent();
    }
}
