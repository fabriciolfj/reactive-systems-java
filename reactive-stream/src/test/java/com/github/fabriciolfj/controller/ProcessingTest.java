package com.github.fabriciolfj.controller;


import com.github.fabriciolfj.message.Processing;
import org.junit.jupiter.api.Test;

public class ProcessingTest {

    @Test
    void testCharacters() {
        var name = Processing.capitalize("fabricio jacob");
        
        System.out.println(name);

    }
}
