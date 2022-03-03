package token;

public class Assertions {

    private Assertions() {
    }

    public static void executeAssertion(Runnable runnable) {
        try {
            runnable.run();
            System.out.println("SUCCESS!");
        } catch (AssertionFail assertionFail) {
            System.err.println(assertionFail.getMessage());
        }
    }

    public static <T> Assertion<T> assertThat(T t) {
        return new Assertion<T>(t);
    }

    public static void fail(String message) {
        throw new AssertionFail(message);
    }

    public static void fail() {
        throw new AssertionFail("Failed");
    }

    public static void fail(Object expected, Object current) {
        throw new AssertionFail(String.format("Expected [%s] but got [%s]", expected, current));
    }

    public record Assertion<T>(T value) {
        public void isEqualTo(T t) {
            if (!t.equals(value)) {
                fail(t, value);
            }
        }

    }
}

class AssertionFail extends RuntimeException {
    public AssertionFail(String message) {
        super(message);
    }
}
