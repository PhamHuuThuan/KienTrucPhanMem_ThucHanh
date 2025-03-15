package org.designpattern;

public class CompositeTest {
    public static void main(String[] args) {
        File file1 = new File("file1");
        File file2 = new File("file2");
        File file3 = new File("file3");

        Folder folder1 = new Folder("folder1");
        Folder folder2 = new Folder("folder2");

        folder1.addObject(file1);
        folder1.addObject(file2);
        folder1.addObject(file3);
        folder1.addObject(folder2);
        folder2.addObject(file1);

        folder1.showInfor();
    }
}
