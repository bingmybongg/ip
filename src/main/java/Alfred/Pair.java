package Alfred;

public class Pair<T, U> {
    private final T t;
    private final U u;

    public Pair(T t, U u) {
        this.t = t;
        this.u = u;
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        try {

            if (other instanceof Pair<?, ?> pair) {
                return pair.t.equals(this.t) && pair.u.equals(this.u);
            }
        } catch (NullPointerException n) {
            Pair<?, ?> pair = (Pair<?, ?>) other;
            return pair.t.equals(this.t) && pair.u == null;
        }

        return false;
    }

    T t() {
        return this.t;
    }

    U u() {
        return this.u;
    }
}
