package operators;

import token.TokenFactory;

public class NumOp extends AST {

    private final Number value;

    public NumOp(TokenFactory.Token token,
                 Number value) {
        super(token);
        this.value = value;
    }

    public Number getValue() {
        return value;
    }
}
