package org.example;

public class EagerInitializedSingleton {

    private static final EagerInitializedSingleton instance = new
            EagerInitializedSingleton();

    //private constructor to avoid client applications to use constructor
    private EagerInitializedSingleton(){}
    public static EagerInitializedSingleton getInstance(){
        return instance;
    }

    //Test
    public static void main(String[] args) {
        // Get the singleton instance
        EagerInitializedSingleton instance1 = EagerInitializedSingleton.getInstance();
        EagerInitializedSingleton instance2 = EagerInitializedSingleton.getInstance();

        // Check if both instances are the same
        if (instance1 == instance2) {
            System.out.println("Both instances are the same. Singleton works!");
        } else {
            System.out.println("Singleton failed. Instances are different.");
        }

        // Print hash codes to verify
        System.out.println("HashCode of instance1: " + instance1.hashCode());
        System.out.println("HashCode of instance2: " + instance2.hashCode());
    }
}
