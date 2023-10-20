package com.mjc.school;

public class MenuPrinter {
    public void print() {
        System.out.println("""
                Enter the number of operation:
                1 - Get all news.
                2 - Get news by id.
                3 - Create news.
                4 - Update news.
                5 - Remove news by id.
                6 - Get all authors.
                7 - Get author by id.
                8 - Create author.
                9 - Update author.
                10 - Remove author by id.
                0 - Exit.""");
    }
}
