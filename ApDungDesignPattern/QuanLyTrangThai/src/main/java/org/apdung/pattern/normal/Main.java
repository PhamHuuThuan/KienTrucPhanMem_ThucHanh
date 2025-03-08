package org.apdung.pattern.normal;

public class Main {
    public static void main(String[] args) {
        Order order = new Order();
        System.out.println("ORDER STATE: " + order.getState());

        boolean check = order.changeState(State.DELIVERED);
        if (!check) {
            System.out.println("ERROR: Order is not delivered");
        }
        System.out.println("ORDER STATE: " + order.getState());

        check = order.changeState(State.PROCESSING);
        if (!check) {
            System.out.println("ERROR: Order is not processing");
        }
        System.out.println("ORDER STATE: " + order.getState());

        check = order.changeState(State.DELIVERED);
        if (!check) {
            System.out.println("ERROR: Order is not delivered");
        }
        System.out.println("ORDER STATE: " + order.getState());

        check = order.changeState(State.CANCELLED);
        if (!check) {
            System.out.println("ERROR: Order Delivered is not cancelled");
        }
        System.out.println("ORDER STATE: " + order.getState());
    }
}
