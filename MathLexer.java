import token.TokenFactory;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static token.TokenFactory.*;

/*
A Lexer is responsabile to tokenize the input into a stream of Tokens,
 */
public class MathLexer implements Iterator<TokenFactory.Token>, Iterable<TokenFactory.Token> {

    private final String input;
    private final Queue<TokenFactory.Token> tokens;

    public MathLexer(String input) {
        this.input = input;
        this.tokens = lexerAnalysis();
    }

    private Queue<TokenFactory.Token> lexerAnalysis() {
        Queue<Character> inputQueue = createStream(input.toCharArray())
                .collect(Collectors.toCollection(LinkedList::new));
        Queue<TokenFactory.Token> tokens = new LinkedList<>();
        while (!inputQueue.isEmpty()) {
            var currentChar = inputQueue.poll();
            if (Character.isSpaceChar(currentChar)) {
                continue;
            }

            if (Character.isDigit(currentChar)) {
                var result = new StringBuilder(String.valueOf(currentChar));
                while (!inputQueue.isEmpty() && Character.isDigit((currentChar = inputQueue.poll()))) {
                    result.append(currentChar);
                }
                tokens.add(TokenFactory.IntegerToken(Integer.parseInt(result.toString())));
            }

            if (currentChar.equals(PLUS_SIGN)) {
                tokens.add(TokenFactory.PlusToken());
            }

            if (currentChar.equals(MINUS_SIGN)) {
                tokens.add(TokenFactory.MinusToken());
            }

            if (currentChar.equals(DIVISION_SIGN)) {
                tokens.add(TokenFactory.DivisionToken());
            }

            if (currentChar.equals(MULTIPLICATION_SIGN)) {
                tokens.add(TokenFactory.MultiplicationToken());
            }

            if (currentChar.equals(TokenFactory.LEFT_BRACKET_SIGN)) {
                tokens.add(TokenFactory.LeftBracket());
            }

            if (currentChar.equals(TokenFactory.RIGHT_BRACKET_SIGN)) {
                tokens.add(TokenFactory.RightBracket());
            }
        }

        tokens.add(TokenFactory.EOF());

        return tokens;
    }

    private Stream<Character> createStream(char[] characters) {
        var stream = Stream.<Character>builder();
        for (var currChar : characters) {
            stream.add(currChar);
        }
        return stream.build();
    }

    @Override
    public boolean hasNext() {
        return !tokens.isEmpty();
    }

    @Override
    public TokenFactory.Token next() {
        return tokens.poll();
    }

    @Override
    public Iterator<TokenFactory.Token> iterator() {
        return this;
    }
}
