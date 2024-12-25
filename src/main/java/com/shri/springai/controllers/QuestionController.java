package com.shri.springai.controllers;

import com.shri.springai.records.Answer;
import com.shri.springai.records.Question;
import com.shri.springai.services.AIService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ai")
public class QuestionController {

    private final AIService mistraAIService;

    public QuestionController(@Qualifier("mistralAIService") AIService mistraAIService) {
        this.mistraAIService = mistraAIService;
    }

    @PostMapping("/ask")
    public Answer askQuestion(@RequestBody Question question) {
        return mistraAIService.getAnswer(question);
    }
}
