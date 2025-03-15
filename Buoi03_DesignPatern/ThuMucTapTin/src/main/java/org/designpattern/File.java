package org.designpattern;

public class File implements Object {
    private String name;

    public File(String name) {
        this.name = name;
    }

    @Override
    public void showInfor() {
        System.out.println("___File: " + name);
    }
}
