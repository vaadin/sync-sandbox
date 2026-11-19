package com.vaadin.sandbox;

public class Greeter {

    public String greet(String name) {
<<<<<<< HEAD
        if (name == null || name.strip().isEmpty() || name.contains("<")) {
=======
        if (name == null || name.strip().isEmpty() || name.contains("<")) {
>>>>>>> a774efb (Harden input handling (#1))
            throw new IllegalArgumentException("name is required");
        }
        return "Hello, " + name + "!";
    }
}
