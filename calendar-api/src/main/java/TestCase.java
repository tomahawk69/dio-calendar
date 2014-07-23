import java.util.Map;

/**
 * Created by iovchynnikov on 7/22/2014.
 */
public class TestCase {
    private String columnName;
    private Object value;
    private ComparisionType type;

    public enum ComparisionType {
        EQUALS, GREATER, LESS, INLIST
    }

    public TestCase(String columnName, Object value, ComparisionType type) {
        this.columnName = columnName;
        this.value = value;
        this.type = type;
    }

    public Boolean isEqual(Map<String, Object> row) {
        return true;

    }
}
