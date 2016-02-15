package metadata;

import interpreter.exception.SyntaxErrorException;

import java.util.ArrayList;
import java.util.List;

public class Table {

    private String name;
    private List<Attribute> attributeList;
    private Integer primaryKeyIndex;

    public Table(String name, List<Attribute> attributeList, Integer primaryKeyIndex) {
        this.name = name;
        this.attributeList = attributeList;
        this.primaryKeyIndex = primaryKeyIndex;
    }

    public static Table newInstance(String name, String[] definition) throws SyntaxErrorException {

        List<Attribute> attributeList = new ArrayList<>();
        for (int i = 0; i < definition.length; ++i) {
            Attribute attribute = Attribute.newInstance(definition[i]);
            attributeList.add(attribute);
        }
        return new Table(name, attributeList, null);
    }
}
