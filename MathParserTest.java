import token.AssertBuilder;

public class MathParserTest {

    public static void main(String[] args) {
        var parses = new MathParser(new MathLexer("10+20*30"));
        testAdditionAndSubtraction();
    }

    public static void testAdditionAndSubtraction(){
        var parsers = new MathParser(new MathLexer("5+5-3"));
        AssertBuilder.assertThat(parsers.calculate()).isEqualTo(7);
    }
}
