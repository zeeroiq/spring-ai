package com.shri.springai.services;

import com.shri.springai.records.*;

public interface AIService {

    String getAnswer(String question);

    default String getAnswer(String question, String model) {
        throw new RuntimeException("Not implemented");
    }

    default Answer getAnswer(Question question){
        throw new RuntimeException("Not implemented");
    }

    default Answer getCapital(GetCapitalRequest getCapitalRequest){
        throw new RuntimeException("Not implemented");
    }

    default Answer getCapitalInfo(GetCapitalRequest getCapitalRequest) {
        throw new RuntimeException("Not implemented");
    }

    default GetCapitalInfoResponse getCapitalInfoFormatBinding(GetCapitalRequest getCapitalRequest) {
        throw new RuntimeException("Not implemented");
    }

    default GetCapitalResponse getCapitalJsonFormat(GetCapitalRequest getCapitalRequest) {
        throw new RuntimeException("Not implemented");
    }
}
