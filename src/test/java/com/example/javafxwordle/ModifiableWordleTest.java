package com.example.javafxwordle;

import org.junit.*;

/*
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
 */

import static org.junit.jupiter.api.Assertions.*;

public class ModifiableWordleTest {

    MainApplication ma;
    MainController mc;
    MainHelper mh;

    @Before
    public void setup() {
        ma = new MainApplication();
        mc = new MainController();
    }

    @Test
    public void shouldInitializeWordLists() {
        ma.initializeWordLists();

        assertFalse(MainApplication.winningWords.isEmpty(), "Winning words list is empty");
        assertFalse(MainApplication.dictionaryWords.isEmpty(), "Dictionary words list is empty");
    }

    @Test
    public void shouldGetWordOfLengthFive() {
        mc.getRandomWord();
    }



}


