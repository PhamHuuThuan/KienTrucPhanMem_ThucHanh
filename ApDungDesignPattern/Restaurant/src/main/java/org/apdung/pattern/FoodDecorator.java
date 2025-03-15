package org.apdung.pattern;

public abstract class RiceDecorator extends  RiceFood {
    protected Food food;

    public RiceDecorator(Food food) {
        this.food = food;
    }

    @Override
    public double getPrice() {
        return food.getPrice();
    }

    @Override
    public String getDescription() {
        return food.getDescription();
    }
}
