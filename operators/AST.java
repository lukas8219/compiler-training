package operators;

import token.TokenFactory;

public class AST {

    private final TokenFactory.Token token;

    public AST(TokenFactory.Token token) {
        this.token = token;
    }

    public TokenFactory.Token getToken() {
        return token;
    }
}
