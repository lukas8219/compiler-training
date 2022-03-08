import operators.AST;
import operators.ASTFactory;
import operators.BinaryOp;
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

    private AST TERMINAL_EXPRESSION() {
        var result = number();

        while (CURRENT_TOKEN.type().isMultiplication() || CURRENT_TOKEN.type().isDivision()) {
            var token = CURRENT_TOKEN;
            if (CURRENT_TOKEN.type().isMultiplication()) {
                eat(TokenLexes.MULTIPLICATION);
            }

            if (CURRENT_TOKEN.type().isDivision()) {
                eat(TokenLexes.DIVISION);
            }

            result = new BinaryOp(token, result, number());
        }

        return result;
    }

    public AST parseAST() {
        return expression();
    }

    private AST expression() {
        var result = TERMINAL_EXPRESSION();

        while (CURRENT_TOKEN.type().isSubtraction() || CURRENT_TOKEN.type().isAddition()) {
            var token = CURRENT_TOKEN;
            if (CURRENT_TOKEN.type().isAddition()) {
                eat(TokenLexes.PLUS);
            }

            if (CURRENT_TOKEN.type().isSubtraction()) {
                eat(TokenLexes.MINUS);
            }

            result = new BinaryOp(token, result, TERMINAL_EXPRESSION());
        }

        return result;
    }

    private AST number() {
        var token = CURRENT_TOKEN;
        if (token.type().isLeftBracket()) {
            eat(TokenLexes.LEFT_BRACKET);
            var result = expression();
            eat(TokenLexes.RIGHT_BRACKET);
            return result;
        } else if (token.type().isSameTypeAs(TokenLexes.INTEGER)) {
            eat(TokenLexes.INTEGER);
            return ASTFactory.NumberOperator(token.getIntValue());
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
