package operators;

import token.TokenFactory;
import token.TokenLexes;

public class ASTFactory {

    private ASTFactory() {
    }

    public static NumOp NumberOperator(Integer value) {
        return new NumOp(TokenFactory.IntegerToken(value), value);
    }

    public static BinaryOp BinaryOperator(TokenLexes operation,
                                   AST right,
                                   AST left) {
        return new BinaryOp(TokenFactory.fromLexe(operation), right, left);
    }
}
