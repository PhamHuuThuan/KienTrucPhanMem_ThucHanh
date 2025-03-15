package org.microservice.CoPhieu;

public interface Observer {
    public void update();
    public void setSubject(Subject s);
}
