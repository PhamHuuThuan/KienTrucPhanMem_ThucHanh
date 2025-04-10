package org.apdung.pattern;

public abstract class FoodDecorator extends SpecificFood {
    protected Food food;

    public FoodDecorator(Food food) {
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
