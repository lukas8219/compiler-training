import operators.AST;
import operators.BinaryOp;
import operators.NumOp;
import token.TokenFactory;

public class MathInterpreter {

    private final MathParser parser;

    public MathInterpreter(MathParser parser) {
        this.parser = parser;
    }

    public Integer interpret() {
        var ast = parser.parseAST();
        return visit(ast);
    }

    private Integer visitNum(NumOp node) {
        return node.getValue();
    }

    private Integer visitBinOp(BinaryOp node) {
        return TokenFactory
                .getForToken(node.getToken().type())
                .apply(visit(node.getLeft()), visit(node.getRight()));
    }

    private Integer visit(AST node) {
        if (node instanceof BinaryOp bin) {
            return visitBinOp(bin);
        }
        if (node instanceof NumOp op) {
            return visitNum(op);
        }
        throw new RuntimeException("Unexpected Token Type");
    }
}
