import java.util.regex.Pattern;

public enum TokenType {
    // Math patterns
    INT("\\d+"),
    FLOAT("\\d+\\.\\d+"),
    PLUS("\\+"),
    MINUS("-"),
    MULTIPLY("\\*"),
    DIVIDE("/"),
    LPAREN("\\("),
    RPAREN("\\)"),
    SIN("sin"),
    COS("cos"),

    // Management
    ILLEGAL("."),
    EOF("");

    public final String pattern;

    TokenType(String pattern) {
        this.pattern = pattern;
    }
}