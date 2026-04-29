# Lab Report 6: Lexical and Syntactic Analysis
**Student:** Chiril-Adam Macari

## 1. Objective
The goal of this lab was to enhance the lexical analyzer (Lexer) from Lab 3 using Regular Expressions and to implement a simple Parser to extract syntactic information and build an Abstract Syntax Tree (AST).

## 2. Theoretical Background
* **Lexical Analysis:** The process of converting a sequence of characters into a sequence of tokens using Regular Expressions to categorize input patterns like numbers, operators, and functions.
* **Parsing:** The process of taking the token stream and verifying its structure against a formal grammar.
* **AST (Abstract Syntax Tree):** A tree representation of the abstract syntactic structure of source code, where each node represents a construct occurring in the source code.

## 3. Implementation Details

### A. TokenType with Regular Expressions
The implementation replaces manual character checking with a `TokenType` enum that maps each token to a specific regex pattern. 
* **Key Patterns:**
    * `FLOAT`: `\d+\.\d+`
    * `INT`: `\d+`
    * `SIN / COS`: Literal string matches `sin` and `cos`.

### B. The Lexer
The updated `Lexer` uses Java’s `Pattern` and `Matcher` classes. It iterates through the input string, matching the most specific patterns first to ensure that floating-point numbers are not incorrectly split into multiple integer tokens.

### C. The Parser (Recursive Descent)
The `Parser` implements three levels of mathematical precedence to ensure correct order of operations:
1.  **Expression:** Addition and Subtraction.
2.  **Term:** Multiplication and Division.
3.  **Factor:** Numbers, trigonometric functions, and parenthesized expressions.

### D. AST Data Structure
The tree is built using three primary node types:
* `NumberNode`: A leaf node containing a numeric value.
* `BinaryOpNode`: A node containing a left child, an operator, and a right child.
* `FunctionNode`: A node representing unary functions like `sin()` or `cos()`.

## 4. Execution and Results
For the input string: `cos(3.14) * 50 - sin(0.5)`

### Generated Token Stream:
```text
Token{type=COS        literal='cos'}
Token{type=LPAREN     literal='('}
Token{type=FLOAT      literal='3.14'}
Token{type=RPAREN     literal=')'}
Token{type=MULTIPLY   literal='*'}
Token{type=INT        literal='50'}
Token{type=MINUS      literal='-'}
Token{type=SIN        literal='sin'}
Token{type=LPAREN     literal='('}
Token{type=FLOAT      literal='0.5'}
Token{type=RPAREN     literal=')'}
```

### Resulting AST Structure:
The final output demonstrates the correct operator precedence and structural extraction:
`((COS(3.14) MULTIPLY 50.0) MINUS SIN(0.5))`

## 5. Conclusion
This implementation successfully bridges the gap between raw text and structural logic. By using Regular Expressions, the Lexer became more robust and less prone to manual errors. The Parser effectively converts the linear stream of tokens into a hierarchical AST, allowing for future evaluation or code generation.
