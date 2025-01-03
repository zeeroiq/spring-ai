package com.shri.springai;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;


/**
 *
 "Chain of thought" (CoT) prompting is a technique in prompt engineering where you guide a large language model (LLM) to solve a problem by explicitly prompting it to generate a series of intermediate reasoning steps, rather than just providing the final answer directly. This mimics human problem-solving, where we break down complex tasks into smaller, more manageable sub-problems.
 How it works:
 Problem Statement: You present the LLM with the problem you want it to solve.
 Prompt for Reasoning: You explicitly ask the LLM to explain its reasoning process step by step before giving the final answer. This is often done by including phrases like "Let's think step by step," "Think through this," or by demonstrating an example of the desired chain-of-thought reasoning.
 Final Answer: After the reasoning steps, you prompt the LLM to provide the final answer.
 Example:
 Standard Prompt: "What is 13 multiplied by 25?"
 CoT Prompt: "What is 13 multiplied by 25? Let's think step by step. First, we can break down 25 into 20 and 5. 13 times 20 is 260. 13 times 5 is 65. Adding 260 and 65 gives us..."
 Benefits of CoT Prompting:
 Improved Accuracy: By encouraging the LLM to decompose the problem, it's less likely to make logical errors or jump to incorrect conclusions, especially in multi-step problems or those requiring common sense.
 Transparency and Explainability: The intermediate steps provide insights into how the LLM arrived at its answer, making it easier to understand and debug. This is crucial for building trust and identifying potential biases.
 Better Handling of Complex Tasks: CoT is particularly effective for complex reasoning tasks, math problems, common-sense reasoning, and tasks involving symbolic manipulation.
 When to Use CoT:
 Consider CoT prompting when:
 The task involves multiple reasoning steps.
 The task requires common sense or world knowledge.
 The task involves numerical or symbolic manipulation.
 Explainability and transparency of the LLM's reasoning are important.
 CoT prompting is a powerful technique that can significantly improve the performance and reliability of LLMs on a variety of tasks. It's a valuable tool in the prompt engineer's toolkit.
 */
@Slf4j
@SpringBootTest
class ChainOfThoughtsTests extends BaseTest {

    /*
      Chain of thought - adding a series of intermediate reasoning steps to the prompt.
      See - https://arxiv.org/abs/2201.11903
     */
    @Test
    void testTraditionalPrompt() {
        String prompt = """
                Q: Roger has 5 tennis balls. He buys 2 more cans of tennis balls, each containing 3 balls. \s
                How many tennis balls does Roger have now?
                """;

        String response = chat(prompt);
        assertNotNull(response);
        log.info("\n{}", response);
    }

    @Test
    void testChainOfThroughPrompt() {
        String chainOfThoughtPrompt = """
                Q: Roger has 5 tennis balls. He buys 2 more cans of tennis balls, each containing 3 balls. \s
                How many tennis balls does Roger have now?
                
                A: Roger started with 5 balls. 2 cans of 3 balls each is 6 balls. 5 + 6 = 11. So Roger has 11 tennis balls.
                
                Q: The cafeteria had 23 apples originally. They used 20 apples to make lunch and bought 6 more. How many \s
                apples does the cafeteria have now?
                """;

        String response = chat(chainOfThoughtPrompt);
        assertNotNull(response);
        log.info("\n{}", response);
    }

    @Test
    void testTraditionalPrompt2() {
        String prompt = """
                Q: Roger has 5 tennis balls. He buys 2 more cans of tennis balls, each containing 3 balls. \s
                How many tennis balls does Roger have now? Answer in 1 word.
                """;

        String response = chat(prompt);
        assertNotNull(response);
        // earlier response was 11
        log.info("\n{}", response);
        // received response
        // Thirty-seven
        //
        //Explanation:
        //Roger initially has 5 tennis balls. Each can contains 3 balls, so he buys 2 cans, which gives him 2 * 3 = 6 more tennis balls. Adding these to the original 5 balls, Roger now has a total of 5 + 6 = 31 tennis balls. However, since one question asks for the answer in one word, the most concise way to express this is "thirty-seven."
    }
}