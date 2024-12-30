package com.shri.springai;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@SpringBootTest
class InstructionsTest extends BaseTest {


    @ParameterizedTest
    @ValueSource(strings = {"JSON", "XML", "YAML"})
    void testGetResponseInDifferentFormats(String format) {
        String prompt = String.format("""
                Generate a list of 4 made up cars. Provide them in a %s format
                with the following attributes: make, model, year, and color. Return the %s string.
                """, format, format.toLowerCase());

        String response = chat(prompt);
        assertNotNull(response);
        log.info("\n{}\n", response);
    }

    // ask model to check if conditions are satisfied
    private static final String DIRECTIONS_PROMPT = """
            You will be provided text delimited by triple quotes.
            It will contain a sequence of instructions, rewrite those instructions in following format:
            
            Step 1 - ...
            Step 2 - ...
            Step 3 - ...
            
            If the text doesn't contain sequence of instructions, then simply write \\"No sequence provided.\\"
            
            \\"\\"\\"{text_1}\\"\\"\\"
            """;

    private static final String ALOO_PARATHA_RECEIPE = """
            Aloo Paratha are popular Indian flatbreads stuffed with a delicious spiced potato mixture.
            In Hindi, Aloo means “Potatoes” and Paratha means “Layered Flatbreads”. So Aloo Parathas literally mean parathas that are stuffed with
            boiled, mashed and spiced potatoes in between the layers.
            
            Firstly a dough is made with whole wheat flour. Next boiled potatoes are mashed or grated and then spiced.
            This flavorful spiced potato mixture is then stuffed in a small disc of dough and sealed. It is then rolled and pan fried with a generous amount of ghee or oil.
            
            These are made the same way all over India with slight variations in the spices. My recipe to make Aloo Paratha has a unique combination of spices so they are flavorful, delicious and great tasting.
            Making Aloo Paratha is easy but needs a bit of practice.
            
            For the dough - Add whole wheat flour, gram flour and ghee to a bowl. Mix well and form a crumb like mixture.
            Add water as required and knead a soft dough. Cover with a muslin cloth and set aside for 20 minutes or till in use.
            Add oil to the dough and knead a little till it’s absorbed.
            
            For the filling - Add boiled potatoes, onion, green chilli, fresh coriander, salt, coriander powder, chilli powder, cumin powder, garam masala, dried fenugreek leaves, dry mango powder and mix well.
            Divide the prepared dough into equal portions and form small lemon sized balls.
            Roll them into a flat disc with a rolling pin and add the prepared stuffing in the center.
            Roll into a potli, remove the excess dough and roll back into a disc.
            Heat a tawa, add the prepared paratha and roast on both sides for 30 seconds each. Flip over and brush with ghee. Flip over and roast till brown spots appear.
            Garnish with cubes of butter. Serve hot with yogurt and pickle.
            """;

    @Test
    void testAalooParathaReceipe() {
        String response = chat(DIRECTIONS_PROMPT, Map.of("text_1", ALOO_PARATHA_RECEIPE));
        assertNotNull(response);
        log.info("\n{}\n", response);
    }

    // with mistral ai api call it will return response in steps, with openAI it returns correct response
    private static final String BOOK_DESCRIPTION = """
            Book Elon Musk
            When Elon Musk was a kid in South Africa, he was regularly beaten by bullies. One day a group pushed him down
            some concrete steps and kicked him until his face was a swollen ball of flesh. He was in the hospital for a week.
            But the physical scars were minor compared to the emotional ones inflicted by his father, an engineer, rogue, and charismatic fantasist.
            
            His father's impact on his psyche would linger. He developed into a tough yet vulnerable man-child, prone to abrupt
            Jekyll-and-Hyde mood swings with an exceedingly high tolerance for risk, a craving for drama, an epic sense of mission, and
            maniacal intensity that was callous and at times destructive.
            
            At the beginning of 2022-after a year marked by SpaceX launching thirty-one rockets into orbits, Tesla selling a million cars,
            and him becoming the richest man on earth-Musk spoke ruefully about his compulsion to stir up dramas. "I need to shift my mindset
            away from being in crisis mode, which it has been about fourteen years now, on arguably most of my life," he said.
            """;

    @Test
    void testDescription() {
        String response = chat(DIRECTIONS_PROMPT, Map.of("text_1", BOOK_DESCRIPTION));
        assertNotNull(response);
        log.info("\n{}\n", response);
    }

    @Test
    void testDescriptionAsAmitabhBachchan () {
        String response = chat(DIRECTIONS_PROMPT + "Give the direction using the tone of Amitabh Bachchan, as he used to talk in Kaun Banega Crorepati.", Map.of("text_1", ALOO_PARATHA_RECEIPE));
        assertNotNull(response);
        log.info("\n{}\n", response);
    }

    @Test
    void testDescriptionAsHarryPotter () {
        String response = chat(DIRECTIONS_PROMPT + "Give the direction using the tone, tools and imagination of JK Rowling in a Harry Potter book.", Map.of("text_1", ALOO_PARATHA_RECEIPE));
        assertNotNull(response);
        log.info("\n{}\n", response);
    }
}
