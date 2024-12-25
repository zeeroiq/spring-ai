package com.shri.springai.services;

public interface AIService {

    String getAnswer(String question);
    String getAnswer(String question, String model);
}
