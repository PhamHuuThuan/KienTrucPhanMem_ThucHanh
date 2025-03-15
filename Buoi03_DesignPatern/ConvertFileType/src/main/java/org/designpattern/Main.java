package org.designpattern;

public class Main {
    public static void main(String[] args) {
        try {
            DataAdapter adapter = new XmlJsonAdapter();

            String xml = "<user><name>John Doe</name><age>30</age></user>";
            System.out.println("Original XML:\n" + xml);

            // Chuyển đổi từ XML sang JSON
            String json = adapter.convertToJson(xml);
            System.out.println("Converted JSON:\n" + json);

            // Chuyển đổi ngược từ JSON sang XML
            String backToXml = adapter.convertToXml(json);
            System.out.println("Converted back to XML:\n" + backToXml);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}