package com.shri.springai.services;

import com.shri.springai.records.Answer;
import com.shri.springai.records.Question;

public interface AIService {

    String getAnswer(String question);

    default String getAnswer(String question, String model) {
        throw new RuntimeException("Not implemented");
    }

    default Answer getAnswer(Question question){
        throw new RuntimeException("Not implemented");
    }
}
