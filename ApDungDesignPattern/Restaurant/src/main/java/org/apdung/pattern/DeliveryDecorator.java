package org.apdung.pattern;

public class DeliveryDecorator extends FoodDecorator {
    public DeliveryDecorator(Food food) {
        super(food);
    }

    @Override
    public double getPrice() {
        return food.getPrice()+15;
    }

    @Override
    public String getDescription() {
        return food.getDescription() + " + Delivery";
    }
}
