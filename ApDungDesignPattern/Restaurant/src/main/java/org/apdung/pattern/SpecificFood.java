package org.apdung.pattern;

public class SpecificFood extends  Food{
    public SpecificFood(String name) {
        description = "Rice";
    }

    @Override
    public double getPrice() {
        return 50000;
    }
}
