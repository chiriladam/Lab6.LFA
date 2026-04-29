import java.util.List;

public class Parser {
    private final List<Token> tokens;
    private int current = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    public ExprNode parse() {
        return expression();
    }

    private ExprNode expression() {
        ExprNode node = term();
        while (match(TokenType.PLUS, TokenType.MINUS)) {
            TokenType op = previous().type;
            ExprNode right = term();
            node = new BinaryOpNode(node, op, right);
        }
        return node;
    }

    private ExprNode term() {
        ExprNode node = factor();
        while (match(TokenType.MULTIPLY, TokenType.DIVIDE)) {
            TokenType op = previous().type;
            ExprNode right = factor();
            node = new BinaryOpNode(node, op, right);
        }
        return node;
    }

    private ExprNode factor() {
        if (match(TokenType.INT, TokenType.FLOAT)) {
            return new NumberNode(previous().literal);
        }
        if (match(TokenType.SIN, TokenType.COS)) {
            TokenType function = previous().type;
            consume(TokenType.LPAREN, "Expect '(' after function");
            ExprNode arg = expression();
            consume(TokenType.RPAREN, "Expect ')' after argument");
            return new FunctionNode(function, arg);
        }
        if (match(TokenType.LPAREN)) {
            ExprNode node = expression();
            consume(TokenType.RPAREN, "Expect ')' after expression");
            return node;
        }
        throw new RuntimeException("Unexpected token: " + peek().literal);
    }

    private boolean match(TokenType... types) {
        for (TokenType type : types) {
            if (check(type)) {
                advance();
                return true;
            }
        }
        return false;
    }

    private boolean check(TokenType type) {
        return !isAtEnd() && peek().type == type;
    }

    private Token advance() {
        if (!isAtEnd()) current++;
        return previous();
    }

    private boolean isAtEnd() { return peek().type == TokenType.EOF; }
    private Token peek() { return tokens.get(current); }
    private Token previous() { return tokens.get(current - 1); }
    private void consume(TokenType type, String message) {
        if (check(type)) advance();
        else throw new RuntimeException(message);
    }
}