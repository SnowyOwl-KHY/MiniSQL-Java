package metadata;

import interpreter.exception.AttributeTypeErrorException;

import java.util.HashMap;
import java.util.Map;

public class AttributeType {
    private String type;
    private int size;

    private static Map<String, Integer> typeSize = new HashMap<>();
    static {
        typeSize.put("int", 4);
    }

    private AttributeType(String type, int size) {
        this.type = type;
        this.size = size;
    }

    public static AttributeType newInstance(String type) throws AttributeTypeErrorException {
        Integer size;
        if (type.startsWith("char")) {
            int leftBracketIndex = type.indexOf('(');
            if (leftBracketIndex == -1 || !type.endsWith(")")) {
                throw new AttributeTypeErrorException("Size of char has not been specified.");
            }
            try {
                size = Integer.parseInt(type.substring(leftBracketIndex + 1, type.length() - 1));
            } catch (NumberFormatException e) {
                throw new AttributeTypeErrorException("Wrong format of size of char");
            }
            type = "char";
        } else {
            size = typeSize.get(type);
            if (size == null) {
                throw new AttributeTypeErrorException("Unknown type.");
            }
        }
        return new AttributeType(type, size);
    }
}
