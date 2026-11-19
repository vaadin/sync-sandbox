package com.vaadin.sandbox;

public class Greeter {

    public String greet(String name) {
        if (name == null || name.strip().isEmpty() || name.contains("<")) {
            throw new IllegalArgumentException("name is required");
        }
        return "Hello, " + name + "!";
    }
}
