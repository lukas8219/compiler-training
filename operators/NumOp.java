package operators;

import token.TokenFactory;

public class NumOp extends AST {

    private final Integer value;

    public NumOp(TokenFactory.Token token,
                 Integer value) {
        super(token);
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
