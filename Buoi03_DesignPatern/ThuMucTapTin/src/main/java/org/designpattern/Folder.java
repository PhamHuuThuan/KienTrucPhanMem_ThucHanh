package org.designpattern;

import java.util.ArrayList;
import java.util.List;

public class Folder implements Object {
    private String name;
    private List<Object>  objects;
    public Folder(String name) {
        this.name = name;
        this.objects = new ArrayList<Object>();
    }

    //add
    public void addObject(Object object) {
        this.objects.add(object);
    }

    //remove
    public void removeObject(Object object) {
        this.objects.remove(object);
    }

    //format
    public void formatFolder() {
        this.objects.clear();
    }

    @Override
    public void showInfor() {
        System.out.println("___Folder: " + name);
        for (Object object : objects) {
            System.out.print("\t");
            object.showInfor();
        }
    }
}
