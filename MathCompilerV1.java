import token.Assertions;
import token.TokenFactory;
import token.TokenLexes;

public class MathCompilerV1 {

    private final String INPUT;
    private int TOKEN_IDX = 0;
    private TokenFactory.Token CURRENT_TOKEN;
    private Character CURRENT_CHAR;


    public MathCompilerV1(String input) {
        this.INPUT = input;
        CURRENT_CHAR = INPUT.charAt(TOKEN_IDX);
        CURRENT_TOKEN = getNextToken();
    }

    public static void main(String[] args) {
        var one = new MathCompilerV1("7 + 3 * (10 / (12 / (3 + 1) - 1))").start();
        var two = new MathCompilerV1(" 3").start();
        var three = new MathCompilerV1("14 + 2 * 3 - 6 / 2").start();
        var four = new MathCompilerV1("7 + 3 * (10 / (12 / (3 + 1) - 1)) / (2 + 3) - 5 - 3 + (8)").start();

        Assertions.executeAssertion(() -> {
            Assertions.assertThat(one).isEqualTo(22);
            Assertions.assertThat(two).isEqualTo(3);
            Assertions.assertThat(three).isEqualTo(17);
            Assertions.assertThat(four).isEqualTo(10);
        });
    }

    private void advanceTokenIdx() {
        TOKEN_IDX++;
        if (TOKEN_IDX > INPUT.length() - 1) {
            CURRENT_CHAR = null;
        } else {
            CURRENT_CHAR = INPUT.charAt(TOKEN_IDX);
        }
    }

    private Integer parseInteger() {
        final var sb = new StringBuilder();
        while (CURRENT_CHAR != null && Character.isDigit(CURRENT_CHAR)) {
            sb.append(CURRENT_CHAR);
            advanceTokenIdx();
        }
        if (sb.isEmpty()) {
            return 0;
        }
        return Integer.parseInt(sb.toString());
    }


    private void skipWhiteSpaces() {
        while (Character.isSpaceChar(CURRENT_CHAR)) {
            advanceTokenIdx();
        }
    }

    private TokenFactory.Token getNextToken() {
        while (CURRENT_CHAR != null) {
            if (Character.isSpaceChar(CURRENT_CHAR)) {
                skipWhiteSpaces();
                continue;
            }
            if (Character.isDigit(CURRENT_CHAR)) {
                return TokenFactory.IntegerToken(parseInteger());
            }

            if (CURRENT_CHAR.equals(TokenFactory.MINUS_SIGN)) {
                advanceTokenIdx();
                return TokenFactory.MinusToken();
            }

            if (CURRENT_CHAR.equals(TokenFactory.PLUS_SIGN)) {
                advanceTokenIdx();
                return TokenFactory.PlusToken();
            }

            if (CURRENT_CHAR.equals(TokenFactory.DIVISION_SIGN)) {
                advanceTokenIdx();
                return TokenFactory.DivisionToken();
            }

            if (CURRENT_CHAR.equals(TokenFactory.MULTIPLICATION_SIGN)) {
                advanceTokenIdx();
                return TokenFactory.MultiplicationToken();
            }

            if (CURRENT_CHAR.equals(TokenFactory.LEFT_BRACKET_SIGN)) {
                advanceTokenIdx();
                return TokenFactory.LeftBracket();
            }

            if (CURRENT_CHAR.equals(TokenFactory.RIGHT_BRACKET_SIGN)) {
                advanceTokenIdx();
                return TokenFactory.RightBracket();
            }

            throw new RuntimeException("Unexpected Token");
        }
        return TokenFactory.EOF();
    }

    private Integer start() {
        return expr();
    }

    private Integer expr() {
        int result = terminal();

        while (CURRENT_TOKEN.type().isSubtraction() || CURRENT_TOKEN.type().isAddition()) {

            var token = CURRENT_TOKEN;
            if (token.type().isAddition()) {
                consumeToken(TokenLexes.PLUS);
                result += terminal();
            }

            if (token.type().isSubtraction()) {
                consumeToken(TokenLexes.MINUS);
                result -= terminal();
            }

        }

        return result;
    }

    private int terminal() {
        int result = factor();
        while (CURRENT_TOKEN.type().isDivision() || CURRENT_TOKEN.type().isMultiplication()) {
            var token = CURRENT_TOKEN;
            if (token.type().isMultiplication()) {
                consumeToken(TokenLexes.MULTIPLICATION);
                result *= factor();
            }

            if (token.type().isDivision()) {
                consumeToken(TokenLexes.DIVISION);
                result /= factor();
            }
        }

        return result;
    }

    private int factor() {
        var token = CURRENT_TOKEN;
        if (token.type().isLeftBracket()) {
            consumeToken(TokenLexes.LEFT_BRACKET);
            var result = expr();
            consumeToken(TokenLexes.RIGHT_BRACKET);
            return result;
        }
        consumeToken(TokenLexes.INTEGER);
        return token.getIntValue();
    }

    private void consumeToken(TokenLexes integer) {
        if (CURRENT_TOKEN.type().isSameTypeAs(integer)) {
            CURRENT_TOKEN = getNextToken();
            return;
        }
        throw new RuntimeException("Parsing error");
    }


}