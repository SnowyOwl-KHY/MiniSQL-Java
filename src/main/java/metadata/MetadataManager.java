package metadata;

import interpreter.exception.SyntaxErrorException;
import interpreter.exception.MetadataException;

import java.util.HashMap;
import java.util.Map;

public class MetadataManager {

    private static MetadataManager instance = null;

    private MetadataManager() {
    }

    public static MetadataManager getInstance() {
        if (instance == null) {
            instance = new MetadataManager();
        }
        return instance;
    }

    private Map<String, Table> tables = new HashMap<>();

    public void createTable(String name, String[] definition) throws SyntaxErrorException, MetadataException {
        if (tables.containsKey(name)) {
            throw new MetadataException("Table '" + name + "' already exists.");
        }
        Table table = Table.newInstance(name, definition);
        tables.put(name, table);
    }

    public void dropTable(String name) throws MetadataException {
        if (tables.remove(name) == null) {
            throw new MetadataException("Table '" + name + "' doesn't exist.");
        }
    }

}
