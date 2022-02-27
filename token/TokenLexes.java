package token;

public enum TokenLexes {
    INTEGER,
    PLUS,
    MINUS,
    DIVISION,
    MULTIPLICATION,
    LEFT_BRACKET,
    RIGHT_BRACKET,
    EOF;

    public boolean isLeftBracket() {
        return this == TokenLexes.LEFT_BRACKET;
    }

    public boolean isRightBracket() {
        return this == TokenLexes.RIGHT_BRACKET;
    }

    public boolean isOperation() {
        return isAddition() || isDivision() || isMultiplication() || isSubtraction();
    }

    public boolean isSameTypeAs(TokenLexes inputLexe) {
        return this.equals(inputLexe);
    }

    public boolean isMultiplication() {
        return this == TokenLexes.MULTIPLICATION;
    }

    public boolean isDivision() {
        return this == TokenLexes.DIVISION;
    }

    public boolean isAddition() {
        return this == TokenLexes.PLUS;
    }

    public boolean isSubtraction() {
        return this == TokenLexes.MINUS;
    }
}