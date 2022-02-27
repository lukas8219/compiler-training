package operators;

import token.TokenFactory;

public class BinaryOp extends AST {

    private final TokenFactory.Token left;
    private final TokenFactory.Token right;

    public BinaryOp(TokenFactory.Token token,
                    TokenFactory.Token left,
                    TokenFactory.Token right) {
        super(token);
        this.left = left;
        this.right = right;
    }
}
