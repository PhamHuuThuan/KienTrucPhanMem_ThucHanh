package org.apdung.pattern;

public class DrinkDecorator extends RiceDecorator {
    public DrinkDecorator(Food food) {
        super(food);
    }

    @Override
    public double getPrice() {
        return food.getPrice() + 20;
    }

    @Override
    public String getDescription() {
        return food.getDescription() + " + Drink";
    }

}
