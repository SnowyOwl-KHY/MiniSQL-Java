package interpreter;

import interpreter.exception.CommandParseException;
import interpreter.exception.SyntaxErrorException;
import interpreter.exception.UnFinishedCommandException;
import metadata.MetadataManager;
import interpreter.exception.MetadataException;

public class Interpreter {

    private String command;
    private int index = 0;

    private void initial(String command) {
        this.command = command;
        index = 0;
    }

    private String trim(String string) {
        string = string.trim();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < string.length(); ++i) {
            char c = string.charAt(i);
            if (c != ' ' && c != '\n') {
                stringBuilder.append(c);
            } else {
                char lastChar = stringBuilder.charAt(stringBuilder.length() - 1);
                if (lastChar != ' ') {
                    stringBuilder.append(' ');
                }
            }
        }
        return stringBuilder.toString();
    }

    public void execute(String command) {
        if (command == null) {
            return;
        }
        command = trim(command);
        if (command.isEmpty()) {
            return;
        }

        initial(command);

        try {
            String commandType;
            try {
                commandType = nextWord().toLowerCase();
            } catch (NoWordException e) {
                throw new UnFinishedCommandException("Lack command type.");
            }

            if (commandType.equals("create")) {

                create();

            } else if (commandType.equals("drop")) {

                drop();

            }

        } catch (CommandParseException e) {
            e.printMessage();
        }
    }

    private boolean isSeparator(char c) {
        return !(Character.isLetterOrDigit(c) || c == '_');
    }

    private String nextWord() throws NoWordException {
        for (; index < command.length(); ++index) {
            if (!isSeparator(command.charAt(index))) {
                break;
            }
        }
        if (index >= command.length()) {
            throw new NoWordException();
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (; index < command.length(); ++index) {
            char c = command.charAt(index);
            if (isSeparator(c)) {
                break;
            } else {
                stringBuilder.append(c);
            }
        }
        return stringBuilder.toString();
    }

    private static class NoWordException extends CommandParseException {
    }

    private void create() throws CommandParseException {
        String creationType;
        try {
            creationType = nextWord().toLowerCase();
        } catch (NoWordException e) {
            throw new UnFinishedCommandException("Lack creation type.");
        }

        if (creationType.equals("table")) {
            createTable();
        }
    }

    private void createTable() throws UnFinishedCommandException, SyntaxErrorException, MetadataException {
        String tableName;
        try {
            tableName = nextWord();
        } catch (NoWordException e) {
            throw new UnFinishedCommandException("Lack table name.");
        }

        index = command.indexOf('(', index);
        if (index == -1) {
            throw new SyntaxErrorException("Left bracket not found.");
        }
        if (!command.endsWith(")")) {
            throw new SyntaxErrorException("Right bracket not found.");
        }

        String[] definition = command.substring(index + 1, command.length() - 1).split(",");
        MetadataManager.getInstance().createTable(tableName, definition);
    }

    private void drop() throws CommandParseException {
        String dropType;
        try {
            dropType = nextWord().toLowerCase();
        } catch (NoWordException e) {
            throw new UnFinishedCommandException("Lack drop type.");
        }

        if (dropType.equals("table")) {
            dropTable();
        }
    }

    private void dropTable() throws UnFinishedCommandException, SyntaxErrorException, MetadataException {
        String tableName;
        try {
            tableName = nextWord();
        } catch (NoWordException e) {
            throw new UnFinishedCommandException("Lack table name.");
        }

        try {
            nextWord(); // It should throw an exception.
            throw new SyntaxErrorException("Cannot drop multiple table at once.");
        } catch (NoWordException e) {
            // Normal situation
        }

        MetadataManager.getInstance().dropTable(tableName);
    }

}
