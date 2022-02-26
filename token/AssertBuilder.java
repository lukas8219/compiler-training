package token;

public class AssertBuilder {

    private AssertBuilder() {
    }

    public static <T> Assertion<T> assertThat(T t) {
        return new Assertion<T>(t);
    }

    public record Assertion<T>(T value) {

        public void isEqualTo(T t) {
            if (!t.equals(value)) {
                throw new RuntimeException(String.format("[%s] should be [%s]", value, t));
            }
        }

    }
}
