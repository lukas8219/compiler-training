package operators;

import token.TokenFactory;

public class BinaryOp extends AST {

    private final AST left;
    private final AST right;

    public BinaryOp(TokenFactory.Token token,
                    AST left,
                    AST right) {
        super(token);
        if (!token.type().isOperation()) {
            throw new UnsupportedOperationException("Only operations are allowed in Binary OP");
        }
        this.left = left;
        this.right = right;
    }

    public AST getLeft() {
        return left;
    }

    public AST getRight() {
        return right;
    }
}
