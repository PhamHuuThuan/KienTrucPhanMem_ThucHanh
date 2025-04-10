package org.apdung.pattern;

public class Main {
    public static void main(String[] args) {
        Food food = new SpecificFood("Rice");
        System.out.println(food.getDescription());

        food = new DrinkDecorator(food);
        System.out.println(food.getDescription());

        food = new DeliveryDecorator(food);
        System.out.println(food.getDescription());
    }
}
