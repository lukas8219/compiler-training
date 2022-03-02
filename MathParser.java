import token.TokenFactory;
import token.TokenLexes;

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
        var result = number();

        while (CURRENT_TOKEN.type().isSubtraction() || CURRENT_TOKEN.type().isAddition()) {
            if (CURRENT_TOKEN.type().isAddition()) {
                eat(TokenLexes.PLUS);
                result += number();
            }

            if (CURRENT_TOKEN.type().isSubtraction()) {
                eat(TokenLexes.MINUS);
                result -= number();
            }
        }

        return result;
    }

    private int number() {
        var token = CURRENT_TOKEN;
        eat(TokenLexes.INTEGER);
        return token.getIntValue();
    }

    private void eat(TokenLexes token) {
        if (!CURRENT_TOKEN.type().isSameTypeAs(token)) {
            throw new RuntimeException("Parsing error");
        }
        nextToken();
    }
}
