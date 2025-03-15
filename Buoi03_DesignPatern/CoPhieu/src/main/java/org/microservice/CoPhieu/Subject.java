package org.microservice.CoPhieu;

public interface Subject {
    public void add(Observer o);
    public void remove(Observer o);
    public void noti();
    public Object getUpdate(Observer o);
}
