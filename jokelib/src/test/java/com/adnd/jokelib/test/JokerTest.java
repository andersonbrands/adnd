package com.adnd.jokelib.test;

import com.adnd.jokelib.Joker;

import org.junit.Test;

public class JokerTest {

    @Test
    public void test() {
        Joker joker = new Joker();
        assert joker.getJoke().length() != 0;
    }

}
