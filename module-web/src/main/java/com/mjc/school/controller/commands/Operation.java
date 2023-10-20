package com.mjc.school.controller.commands;

public enum Operation {
    GET_ALL_NEWS(1, "Get all news."),
    GET_NEWS_BY_ID(2, "Get news by id."),
    CREATE_NEWS(3, "Create news."),
    UPDATE_NEWS(4, "Update news."),
    REMOVE_NEWS_BY_ID(5, "Remove news by id."),
    GET_ALL_AUTHORS(6, "Get all authors."),
    GET_AUTHOR_BY_ID(7, "Get author by id."),
    CREATE_AUTHOR(8, "Create author."),
    UPDATE_AUTHOR(9, "Update author."),
    REMOVE_AUTHOR_BY_ID(10, "Remove author by id.");;

    private final Integer operationNumber;
    private final String operation;

    public static final String OPERATION = "Operation: ";
    Operation(int operationNumber, String operation) {
        this.operationNumber = operationNumber;
        this.operation = operation;
    }

    public String getOperation() {
        return OPERATION + operation;
    }

    public Integer getOperationNumber() {
        return operationNumber;
    }
}
