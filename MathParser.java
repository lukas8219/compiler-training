import token.TokenFactory;

import java.util.Iterator;

public class MathParser {

    private final Iterator<TokenFactory.Token> TOKENS;
    private TokenFactory.Token CURRENT_TOKEN;

    public MathParser(Iterable<TokenFactory.Token> tokens) {
        this.TOKENS = tokens.iterator();
        this.CURRENT_TOKEN = TOKENS.next();
    }

    private void nextToken() {
        this.CURRENT_TOKEN = TOKENS.next();
    }

    public Integer calculate() {
        return null;
    }
}
