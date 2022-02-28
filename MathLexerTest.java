import token.AssertBuilder;

import java.util.HashMap;

public class MathLexerTest {
    private static final HashMap<Integer, Object> tokenEntry = new HashMap<>();

    static {
        tokenEntry.put(0, 11);
        tokenEntry.put(1, '+');
        tokenEntry.put(2, 20);
        tokenEntry.put(3, '*');
        tokenEntry.put(4, 30);
    }

    public static void main(String[] args) {


        var lexer = new MathLexer("11+20*30");

        int count = 0;
        while (lexer.hasNext()) {
            var result = tokenEntry.get(count);
            AssertBuilder.assertThat(lexer.next().value()).isEqualTo(result);
            count++;
        }

    }
}
