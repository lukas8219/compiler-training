import token.Assertions;

public class MathParserTest {

    public static void assertParser(String expression, Integer value) {
        var parser = new MathParser(new MathLexer(expression));
        Assertions.assertThat(new MathInterpreter(parser).interpret()).isEqualTo(value);
    }

    public static void main(String[] args) {
        Assertions.executeAssertion(() -> {
            testAdditionAndSubtraction();
            testMultiplicationAndDivision();
            testExpressionWithParenthesis();
        });
    }

    private static void testExpressionWithParenthesis() {
        assertParser("1+(20*10+(20+20*10))", 421);
    }

    public static void testAdditionAndSubtraction() {
        assertParser("3+3+3", 9);
        assertParser("3-3-5", -5);
    }

    private static void testMultiplicationAndDivision() {
        assertParser("3*3-3", 6);
    }
}
