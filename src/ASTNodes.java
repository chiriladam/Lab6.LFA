interface ExprNode {}

// Leaf node: a number
class NumberNode implements ExprNode {
    double value;
    NumberNode(String val) { this.value = Double.parseDouble(val); }
    @Override public String toString() { return String.valueOf(value); }
}

// Branch node: operation with two sides
class BinaryOpNode implements ExprNode {
    ExprNode left;
    TokenType op;
    ExprNode right;

    BinaryOpNode(ExprNode left, TokenType op, ExprNode right) {
        this.left = left;
        this.op = op;
        this.right = right;
    }
    @Override public String toString() {
        return String.format("(%s %s %s)", left, op, right);
    }
}

// Unary node: sin(x) or cos(x)
class FunctionNode implements ExprNode {
    TokenType function;
    ExprNode argument;

    FunctionNode(TokenType function, ExprNode argument) {
        this.function = function;
        this.argument = argument;
    }
    @Override public String toString() {
        return String.format("%s(%s)", function, argument);
    }
}