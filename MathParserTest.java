import token.AssertBuilder;

public class MathParserTest {

    public static void assertParser(String expression, Integer value){
        AssertBuilder.assertThat(new MathParser(new MathLexer(expression)).calculate()).isEqualTo(value);
    }

    public static void main(String[] args) {
        testAdditionAndSubtraction();
        testMultiplicationAndDivision();
        testExpressionWithParenthesis();
    }

    private static void testExpressionWithParenthesis() {
        assertParser("1+(20*10+(20+20*10))", 421);
    }

    public static void testAdditionAndSubtraction(){
        assertParser("3+3+3", 9);
        assertParser("3-3-5", -5);
    }

    private static void testMultiplicationAndDivision(){
        assertParser("3*3-3", 6);
    }
}
