package com.shri.springai;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;


/**
 * In prompt engineering, "zero-shot," "few-shot," and (implicitly) "many-shot" refer to how many examples you provide in the prompt to guide the language model (LLM) toward the desired output.
 * Zero-Shot Prompting: You give the LLM a task without any explicit examples. You rely on the LLM's pre-trained knowledge and ability to understand natural language.
 * Classify the sentiment of the following text: "This movie is fantastic!"
 * Few-Shot Prompting: You provide a small number of examples (typically 1-5) along with the task description. This gives the LLM a better understanding of your expectations.
 * Classify the sentiment of the following text:
 * Text: "This movie is terrible." Sentiment: Negative
 * Text: "I loved this book!" Sentiment: Positive
 * Text: "This restaurant is okay." Sentiment: Neutral
 * Text: "This product is amazing!" Sentiment: ?
 * Many-Shot Prompting (Often Implicit): You include a larger number of examples in the prompt. This can further improve performance, especially for complex tasks, but it also makes the prompt longer and more computationally expensive. The line between "few-shot" and "many-shot" is somewhat blurry, but generally, more than 5 examples would be considered "many-shot." Often, "many-shot" learning is implemented by fine-tuning a model on a dataset of examples, rather than including all the examples directly in the prompt. Therefore, when talking about prompt engineering, you usually hear the terms "zero-shot" and "few-shot." Then, not using those terms usually implies "many-shot" (or standard fine-tuning).
 * The choice between zero-shot, few-shot, and many-shot depends on the task complexity and the LLM's capabilities. Simpler tasks might be handled well with zero-shot prompting, while more complex or nuanced tasks often benefit from a few examples. Many-shot is useful when you want the best possible results, especially with smaller LLMs
 */
@Slf4j
@SpringBootTest
class ZeroAndFewShotTests extends BaseTest {

    public static final String REVIEW = """
            I get it. Everyone is buying these now after years of not caring about Stanley tumblers because of social media. The problem with viral crap like this is we get caught up in fitting in and jumping on the band wagon that we fail to see what's wrong with a product before buying it.
            THIS TUMBLER IS NOT LEAK PROOF. It's not even a little resistent to leaking. Even if you have the top fully closed and the straw taken out, the liquid inside will leak out like crazy if you tip it over even slightly. To me, if I'm going to carry around 30-40oz of hot or cold liquids then the tumbler MUST prevent said liquids from coming out. I understand it's not a water bottle, but that's a lame technicality that Stanley shouldn't cling to. At a MINIMUM the tumbler should be leak proof if I take out the straw and close the top. Furthermore, the sip top closing mechanism seems very flimsy and can be easily bended out of place, so beware of turning it too hard or especially dropping your tumbler.
            I am highly disappointed for being sucked into thinking this was a reliable tumbler that would replace others I have. Granted they are not as nice looking, but they do the job of holding AND containing the water I take with me all day to and from work in NYC.
            I do NOT recommend this tumbler and I would suggest that Stanley fix these important issues instead of focusing on more colors and patterns.""";

    public static final String PROMPT = """
            Identify a list of emotions that the writer of the following reviews is expressing, and provide a brief summary of each review.
            
            Review: ```{review}```
            """;

    /**
     * Zero shot - send the model a single prompt with no hints or examples. Leverages the model's training to generate a response.
     */
    @Test
    void zeroShotPromptTest() {
        for (int i = 0; i < 3; i++) {
            // java UUID randomUUID is an API cache buster
            String response = chat(PROMPT, Map.of("review", UUID.randomUUID() + "\n" + REVIEW));
            log.info("#################################\n");
            log.info(response);
        }
    }

    @Test
    void zeroShotPromptTestWithModelOptions() {
        // java for loop 3 times
        for (int i = 0; i < 3; i++) {
            // java UUID randomUUID is an API cache buster
            String response = chat(PROMPT, 0.1f, "ministral-3b-latest", Map.of("review", UUID.randomUUID() + "\n" + REVIEW));
            assertNotNull(response);
            log.info("#################################\n");
            log.info(response);
        }
    }

    /**
     * Few shot - send the model a few examples to help it understand the context of the prompt.
     *
     * Example from 'Language Models are Few-Shot Learners' paper: https://arxiv.org/abs/2005.14165
     */
    public static final String WHATPU_PROMPT = """
            A "whatpu" is a small, furry animal native to Tanzania. An example of a sentence that uses
            the word whatpu is:
           \s
            We were traveling in Africa and we saw these very cute whatpus.
            \s
            To do a "farduddle" means to jump up and down really fast. An example of a sentence that uses
                  the word farduddle is:
      \s""";

    @Test
    void testwhatPuPromptFewShotTest() {
        String response = chat(WHATPU_PROMPT);
        assertNotNull(response);
        log.info(response);
    }

    public static final String VACATION_PROMPT = """
            John likes white sand beaches and warm weather.
            
            What are 5 locations John should consider for vacation?
            """;

    @Test
    void testVacationFewShotTest() {
        String response = chat(VACATION_PROMPT);
        assertNotNull(response);
        log.info(response);
    }

    public static final String MATH_PROMPT = """
            2+2 = twotwo
            3+3 = threethree
            4+5 = fourfive
            
            What is 5+7?
            """;

    @Test
    void testMathPromptFewShotTest() {
        String response = chat(MATH_PROMPT);
        assertNotNull(response);
        log.info(response);
    }


    public static final String HALLUCINATION_PROMPT = "Write sales copy for the new 'professional grade' " +
            "Denali Advanced Toothbrush by GMC.";
    @Test
    void AiHallucinationTest() {
        String response = chat(HALLUCINATION_PROMPT);
        assertNotNull(response);
        log.info(response);
    }
}
