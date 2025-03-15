package org.microservice.CoPhieu;

public class NhaDauTu implements Observer {
    private int id;
    private Subject coPhieu;

    public NhaDauTu(int id) {
        this.id = id;
    }

    @Override
    public void update() {
        double newPrice = (double) coPhieu.getUpdate(this);
        System.out.println(this.id + ". Co Phieu New Price: " + newPrice);
    }

    @Override
    public void setSubject(Subject s) {
        this.coPhieu = s;
    }
}
