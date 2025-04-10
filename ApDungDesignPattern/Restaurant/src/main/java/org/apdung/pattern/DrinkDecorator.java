package org.apdung.pattern;

public class DrinkDecorator extends FoodDecorator {
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
