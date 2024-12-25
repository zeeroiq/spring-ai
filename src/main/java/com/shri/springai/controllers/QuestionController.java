package com.shri.springai.controllers;

import com.shri.springai.records.*;
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

    @PostMapping("/capital")
    public Answer getCapital(@RequestBody GetCapitalRequest getCapitalRequest) {
        return mistraAIService.getCapital(getCapitalRequest);
    }

    @PostMapping("/capital-json")
    public GetCapitalResponse getCapitalJsonFormat(@RequestBody GetCapitalRequest getCapitalRequest) {
        return mistraAIService.getCapitalJsonFormat(getCapitalRequest);
    }

    @PostMapping("/capital-info")
    public Answer getCapitalInfo(@RequestBody GetCapitalRequest getCapitalRequest) {
        return mistraAIService.getCapitalInfo(getCapitalRequest);
    }

    @PostMapping("/capital-info-json")
    public GetCapitalInfoResponse getCapitalInfoJsonBinding(@RequestBody GetCapitalRequest getCapitalRequest) {
        return mistraAIService.getCapitalInfoFormatBinding(getCapitalRequest);
    }
}
