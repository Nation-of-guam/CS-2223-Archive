package aiburns.hw4;

// Question 1. complete this class. It must extend Expression
public class Subtract extends Expression{
    final Expression left;
    final Expression right;

    public Subtract(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public double eval() {
        return left.eval() - right.eval();
    }

    @Override
    public String format() {
        return String.format("(%s-%s)", left.format(), right.format());
    }

    @Override
    public int height() {
        return 1+Integer.max(left.height(), right.height());
    }
}
