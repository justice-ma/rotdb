package com.rotdb.auth.api;

public record RegisterResult (
    Long userId,
    String email,
    String username
){}
