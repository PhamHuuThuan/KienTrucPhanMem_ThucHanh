package org.apdung.pattern;

public class RiceFood extends  Food{
    public RiceFood() {
        description = "Rice";
    }

    @Override
    public double getPrice() {
        return 50000;
    }
}
