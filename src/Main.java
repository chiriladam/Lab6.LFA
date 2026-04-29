import java.util.List;

public class Main {
    public static void main(String[] args) {
        String input = "cos(3.14) * 50 - sin(0.5)";

        System.out.println("Input: " + input);

        // 1. Lexical Analysis (Regex)
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();

        System.out.println("\n--- Tokens ---");
        tokens.forEach(System.out::println);

        // 2. Syntactic Analysis (Parser -> AST)
        Parser parser = new Parser(tokens);
        ExprNode ast = parser.parse();

        System.out.println("\n--- Abstract Syntax Tree (AST) ---");
        System.out.println(ast);
    }
}