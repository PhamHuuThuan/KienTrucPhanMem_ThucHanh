package org.designpattern;

public interface DataAdapter {
    String convertToJson(String xml) throws Exception;
    String convertToXml(String json) throws Exception;
}
