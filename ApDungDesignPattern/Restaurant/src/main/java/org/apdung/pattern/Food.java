package org.apdung.pattern;

public abstract class Food {
    protected String description;
    public String getDescription() {
        return description;
    }

    public abstract double getPrice();
}
