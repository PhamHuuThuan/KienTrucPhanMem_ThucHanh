package org.designpattern;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class XmlJsonAdapter implements DataAdapter {
    private final ObjectMapper jsonMapper;
    private final XmlMapper xmlMapper;

    public XmlJsonAdapter() {
        this.jsonMapper = new ObjectMapper();
        this.xmlMapper = new XmlMapper();
    }

    @Override
    public String convertToJson(String xml) throws Exception {
        JsonNode node = xmlMapper.readTree(xml.getBytes());
        return jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(node);
    }

    @Override
    public String convertToXml(String json) throws Exception {
        JsonNode node = jsonMapper.readTree(json);
        return xmlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(node);
    }
}
