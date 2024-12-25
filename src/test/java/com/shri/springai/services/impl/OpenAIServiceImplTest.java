package com.shri.springai.services.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OpenAIServiceImplTest {

    @Autowired
    private OpenAIServiceImpl openAIService;

    @Test
    void getAnswer() {
        String question = "What is the capital of France, one word answer pls?";
        String expectedAnswer = "Paris";
        String actualAnswer = openAIService.getAnswer(question);
        assertEquals(expectedAnswer, actualAnswer);
    }
}