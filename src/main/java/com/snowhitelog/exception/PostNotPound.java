package com.snowhitelog.exception;

/**
 * status -> 404
 */
public class PostNotPound extends SnowhitelogException {

    private static final String MESSAGE = "존재하지 않는 글입니다.";
    public PostNotPound() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
