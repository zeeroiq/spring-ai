package com.shri.springai.records;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public record GetCapitalInfoResponse(@JsonPropertyDescription("This is the capital name") String capital,
                                     @JsonPropertyDescription("This is the population of the capital") Integer population,
                                     @JsonPropertyDescription("This is region of the capital") String region,
                                     @JsonPropertyDescription("This is the language spoken in the capital") String language,
                                     @JsonPropertyDescription("This is the currency used in the capital") String currency) {
}
