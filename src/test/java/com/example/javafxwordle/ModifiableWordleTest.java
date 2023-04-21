package com.example.javafxwordle;

import org.junit.*;

import static org.junit.jupiter.api.Assertions.*;

public class ModifiableWordleTest {

    MainApplication ma;

    @Before
    public void setup() {
        ma = new MainApplication();
    }

    @Test
    public void shouldInitializeWordLists() {
        ma.initializeWordLists();

        assertFalse(MainApplication.winningWords.isEmpty(), "Winning words list is empty");
        assertFalse(MainApplication.dictionaryWords.isEmpty(), "Dictionary words list is empty");
    }

    @Test
    public void shouldGenerateWordOfLengthFive() {
        ma.initializeWordLists();

        assertEquals(ma.getRandomWord().length(), 5, "Random word length is not 5");
    }

}


