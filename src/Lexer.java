import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {
    private final String input;

    public Lexer(String input) {
        this.input = input;
    }

    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();

        // Define order matters: FLOAT must come before INT
        TokenType[] typesToMatch = {
                TokenType.FLOAT, TokenType.INT, TokenType.SIN, TokenType.COS,
                TokenType.PLUS, TokenType.MINUS, TokenType.MULTIPLY, TokenType.DIVIDE,
                TokenType.LPAREN, TokenType.RPAREN
        };

        StringBuilder sb = new StringBuilder();
        for (TokenType type : typesToMatch) {
            sb.append(String.format("|(?<%s>%s)", type.name(), type.pattern));
        }

        Pattern pattern = Pattern.compile(sb.substring(1));
        Matcher matcher = pattern.matcher(input);

        int lastEnd = 0;
        while (matcher.find()) {
            // Check for skipped characters/whitespace
            String skipped = input.substring(lastEnd, matcher.start()).trim();
            if (!skipped.isEmpty()) {
                // If it's not empty after trim, it's truly an ILLEGAL token
                tokens.add(new Token(TokenType.ILLEGAL, skipped));
            }

            // Identify which specific group matched
            for (TokenType type : typesToMatch) {
                if (matcher.group(type.name()) != null) {
                    tokens.add(new Token(type, matcher.group(type.name())));
                    break;
                }
            }
            lastEnd = matcher.end();
        }

        // Check for any trailing illegal characters
        String trailing = input.substring(lastEnd).trim();
        if (!trailing.isEmpty()) {
            tokens.add(new Token(TokenType.ILLEGAL, trailing));
        }

        tokens.add(new Token(TokenType.EOF, ""));
        return tokens;
    }
}