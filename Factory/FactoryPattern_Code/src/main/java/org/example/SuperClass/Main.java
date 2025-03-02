package org.example.SuperClass;

public class Main {
    public static void main(String[] args) {
        Computer pc = ComputerFactory.getComputer("PC", "8GB", "1TB", "2.4GHz");
        Computer server = ComputerFactory.getComputer("Server", "32GB", "10TB", "3.2GHz");

        System.out.println("PC Config: " + pc);
        System.out.println("Server Config: " + server);
    }
}