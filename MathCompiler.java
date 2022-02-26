public class MathCompiler {

    private static final char PLUS_SIGN = '+';
    private static final char MINUS_SIGN = '-';
    private static final char DIVISION_SIGN = '/';
    private static final char MULTIPLICATION_SIGN = '*';
    private static final char LEFT_BRACKET_SIGN = '(';
    private static final char RIGHT_BRACKET_SIGN = ')';

    private final String INPUT;
    private int TOKEN_IDX = 0;
    private TokenFactory.Token CURRENT_TOKEN = null;
    private Character CURRENT_CHAR;

    //Lexer transforms the Input into a Stream of TOKENS
    //Parser reads the Tokens and transforms into Output

    public MathCompiler(String input) {
        this.INPUT = input;
        CURRENT_CHAR = INPUT.charAt(TOKEN_IDX);
        CURRENT_TOKEN = getNextToken();
    }

    public static void main(String[] args) {
        var compiler = new MathCompiler("7 + 3 * (10 / (12 / (3 + 1) - 1))");
        System.out.println(compiler.start());
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

            if (CURRENT_CHAR.equals(MINUS_SIGN)) {
                advanceTokenIdx();
                return TokenFactory.MinusToken();
            }

            if (CURRENT_CHAR.equals(PLUS_SIGN)) {
                advanceTokenIdx();
                return TokenFactory.PlusToken();
            }

            if (CURRENT_CHAR.equals(DIVISION_SIGN)) {
                advanceTokenIdx();
                return TokenFactory.DivisionToken();
            }

            if (CURRENT_CHAR.equals(MULTIPLICATION_SIGN)) {
                advanceTokenIdx();
                return TokenFactory.MultiplicationToken();
            }

            if (CURRENT_CHAR.equals(LEFT_BRACKET_SIGN)) {
                advanceTokenIdx();
                return TokenFactory.LeftBracket();
            }

            if (CURRENT_CHAR.equals(RIGHT_BRACKET_SIGN)) {
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

        while (CURRENT_TOKEN.isSubtraction() || CURRENT_TOKEN.isAddition()) {

            var token = CURRENT_TOKEN;
            if (token.isAddition()) {
                consumeToken(TokenLexes.PLUS);
                result += terminal();
            }

            if (token.isSubtraction()) {
                consumeToken(TokenLexes.MINUS);
                result -= terminal();
            }

        }

        return result;
    }

    private int terminal() {
        int result = factor();
        while (CURRENT_TOKEN.isDivision() || CURRENT_TOKEN.isMultiplication()) {
            var token = CURRENT_TOKEN;
            if (token.isMultiplication()) {
                consumeToken(TokenLexes.MULTIPLICATION);
                result *= factor();
            }

            if (token.isDivision()) {
                consumeToken(TokenLexes.DIVISION);
                result /= factor();
            }
        }

        return result;
    }

    private int factor() {
        var token = CURRENT_TOKEN;
        if (token.isLeftBracket()) {
            consumeToken(TokenLexes.LEFT_BRACKET);
            var result = expr();
            consumeToken(TokenLexes.RIGHT_BRACKET);
            return result;
        }
        consumeToken(TokenLexes.INTEGER);
        return token.getIntValue();
    }

    private void consumeToken(TokenLexes integer) {
        if (CURRENT_TOKEN.isSameTypeAs(integer)) {
            CURRENT_TOKEN = getNextToken();
            return;
        }
        throw new RuntimeException("Parsing error");
    }

    enum TokenLexes {
        INTEGER,
        PLUS,
        MINUS,
        DIVISION,
        MULTIPLICATION,
        LEFT_BRACKET,
        RIGHT_BRACKET,
        EOF
    }

    public static class TokenFactory {

        private TokenFactory() {
        }


        public static Token IntegerToken(Integer value) {
            return new Token(TokenLexes.INTEGER, value);
        }

        public static Token PlusToken() {
            return new Token(TokenLexes.PLUS, PLUS_SIGN);
        }

        public static Token DivisionToken() {
            return new Token(TokenLexes.DIVISION, DIVISION_SIGN);
        }

        public static Token MultiplicationToken() {
            return new Token(TokenLexes.MULTIPLICATION, MULTIPLICATION_SIGN);
        }

        public static Token MinusToken() {
            return new Token(TokenLexes.MINUS, MINUS_SIGN);
        }

        public static Token LeftBracket() {
            return new Token(TokenLexes.LEFT_BRACKET, LEFT_BRACKET_SIGN);
        }

        public static Token RightBracket() {
            return new Token(TokenLexes.RIGHT_BRACKET, RIGHT_BRACKET_SIGN);
        }

        public static Token EOF() {
            return new Token(TokenLexes.EOF, null);
        }

        private record Token(TokenLexes token, Object value) {

            public Integer getIntValue() {
                return (Integer) value;
            }

            public boolean isLeftBracket() {
                return this.token == TokenLexes.LEFT_BRACKET;
            }

            public boolean isRightBracket() {
                return this.token == TokenLexes.RIGHT_BRACKET;
            }

            public boolean isOperation() {
                return isAddition() || isDivision() || isMultiplication() || isSubtraction();
            }

            public boolean isSameTypeAs(TokenLexes inputLexe) {
                return this.token.equals(inputLexe);
            }

            public boolean isMultiplication() {
                return this.token == TokenLexes.MULTIPLICATION;
            }

            public boolean isDivision() {
                return this.token == TokenLexes.DIVISION;
            }

            public boolean isAddition() {
                return this.token == TokenLexes.PLUS;
            }

            public boolean isSubtraction() {
                return this.token == TokenLexes.MINUS;
            }
        }

    }
}