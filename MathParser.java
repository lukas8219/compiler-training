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

    private Integer TERMINAL_EXPRESSION() {
        var result = number();

        while (CURRENT_TOKEN.type().isMultiplication() || CURRENT_TOKEN.type().isDivision()) {
            if (CURRENT_TOKEN.type().isMultiplication()) {
                eat(TokenLexes.MULTIPLICATION);
                result *= number();
            }

            if (CURRENT_TOKEN.type().isDivision()) {
                eat(TokenLexes.DIVISION);
                result /= number();
            }
        }

        return result;
    }

    public int calculate() {
        return expression();
    }

    private Integer expression() {
        int result = TERMINAL_EXPRESSION();

        while (CURRENT_TOKEN.type().isSubtraction() || CURRENT_TOKEN.type().isAddition()) {
            if (CURRENT_TOKEN.type().isAddition()) {
                eat(TokenLexes.PLUS);
                result += TERMINAL_EXPRESSION();
            }

            if (CURRENT_TOKEN.type().isSubtraction()) {
                eat(TokenLexes.MINUS);
                result -= TERMINAL_EXPRESSION();
            }
        }

        return result;
    }

    private int number() {
        var token = CURRENT_TOKEN;
        if (token.type().isLeftBracket()) {
            eat(TokenLexes.LEFT_BRACKET);
            var result = expression();
            eat(TokenLexes.RIGHT_BRACKET);
            return result;
        } else if (token.type().isSameTypeAs(TokenLexes.INTEGER)) {
            eat(TokenLexes.INTEGER);
            return token.getIntValue();
        }
        throw new RuntimeException("Invalid input for number");
    }

    private void eat(TokenLexes token) {
        if (!CURRENT_TOKEN.type().isSameTypeAs(token)) {
            throw new RuntimeException("Parsing error");
        }
        nextToken();
    }
}
