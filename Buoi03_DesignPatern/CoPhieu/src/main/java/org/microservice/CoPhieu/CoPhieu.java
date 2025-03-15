package org.microservice.CoPhieu;

import java.util.ArrayList;
import java.util.List;

public class CoPhieu implements Subject {
    private int id;
    private String name;
    private double price;
    private List<Observer> dsNDT;

    public CoPhieu(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.dsNDT = new ArrayList<>();
    }

    @Override
    public void add(Observer o) {
        this.dsNDT.add(o);
    }

    @Override
    public void remove(Observer o) {
        this.dsNDT.remove(o);
    }

    @Override
    public void noti() {
        for (Observer o : dsNDT) {
            o.update();
        }
    }

    @Override
    public Object getUpdate(Observer o) {
        return this.price;
    }

    public void changePrice(double price){
        System.out.println("Change price to "+price);
        this.price = price;
        noti();
    }
}
