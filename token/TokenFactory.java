package token;

public class TokenFactory {

    public static final char PLUS_SIGN = '+';
    public static final char MINUS_SIGN = '-';
    public static final char DIVISION_SIGN = '/';
    public static final char MULTIPLICATION_SIGN = '*';
    public static final char LEFT_BRACKET_SIGN = '(';
    public static final char RIGHT_BRACKET_SIGN = ')';

    private TokenFactory() {
    }


    public static TokenFactory.Token IntegerToken(Integer value) {
        return new TokenFactory.Token(TokenLexes.INTEGER, value);
    }

    public static TokenFactory.Token PlusToken() {
        return new TokenFactory.Token(TokenLexes.PLUS, PLUS_SIGN);
    }

    public static TokenFactory.Token DivisionToken() {
        return new TokenFactory.Token(TokenLexes.DIVISION, DIVISION_SIGN);
    }

    public static TokenFactory.Token MultiplicationToken() {
        return new TokenFactory.Token(TokenLexes.MULTIPLICATION, MULTIPLICATION_SIGN);
    }

    public static TokenFactory.Token MinusToken() {
        return new TokenFactory.Token(TokenLexes.MINUS, MINUS_SIGN);
    }

    public static TokenFactory.Token LeftBracket() {
        return new TokenFactory.Token(TokenLexes.LEFT_BRACKET, LEFT_BRACKET_SIGN);
    }

    public static TokenFactory.Token RightBracket() {
        return new TokenFactory.Token(TokenLexes.RIGHT_BRACKET, RIGHT_BRACKET_SIGN);
    }

    public static TokenFactory.Token EOF() {
        return new TokenFactory.Token(TokenLexes.EOF, null);
    }

    public static Token fromLexe(TokenLexes operation) {
        return switch (operation) {
            case DIVISION -> DivisionToken();
            case MULTIPLICATION -> MultiplicationToken();
            case PLUS -> PlusToken();
            case MINUS -> MinusToken();
            default -> throw new RuntimeException("Parsing error");
        };
    }

    public record Token(TokenLexes type, Object value) {

        public Integer getIntValue() {
            return (Integer) value;
        }

    }

}