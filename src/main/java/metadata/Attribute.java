package metadata;

import interpreter.exception.AttributeTypeErrorException;
import interpreter.exception.SyntaxErrorException;

public class Attribute {
    private String name;
    private AttributeType type;
    private ConstraintCondition condition;

    private Attribute(String name, AttributeType type, ConstraintCondition condition) {
        this.name = name;
        this.type = type;
        this.condition = condition;
    }

    public static Attribute newInstance(String definitionString) throws SyntaxErrorException {
        String[] definition = definitionString.split(" ");
        if (definition.length < 2) {
            throw new SyntaxErrorException("Attribute type not found.");
        }

        String name = definition[0];
        if (!isLegalIdentifier(name)) {
            throw new SyntaxErrorException("Illegal name for attribute.");
        }

        AttributeType type;
        try {
            type = AttributeType.newInstance(definition[1]);
        } catch (AttributeTypeErrorException e) {
            throw new SyntaxErrorException("type of " + name + " error: " + e.getMessage());
        }

        ConstraintCondition condition;
        condition = ConstraintCondition.newInstance(definition);

        return new Attribute(name, type, condition);
    }

    private static boolean isLegalIdentifier(String identifier) {
        for (int i = 0; i < identifier.length(); ++i) {
            if (!isLegalIdentifierCharacter(identifier.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static boolean isLegalIdentifierCharacter(char c) {
        return c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' || c >= '0' && c <= '9' || c == '_';
    }
}
