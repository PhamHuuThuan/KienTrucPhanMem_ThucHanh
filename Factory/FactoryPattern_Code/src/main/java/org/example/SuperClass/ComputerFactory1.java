package org.example.SuperClass;

public class ComputerFactory1 {
    public static Computer getComputer(ComputerAbstractFactory factory){
        return factory.createComputer();
    }
}
