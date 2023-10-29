package com.mushipeas.summarizr.domain.summarizetext.adapter.model;

import java.io.Serializable;

public record Message(String role, String content) implements Serializable {}
