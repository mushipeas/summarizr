package com.mushipeas.summarizr.model;

import java.io.Serializable;

public record Message(String role, String content) implements Serializable {

}
