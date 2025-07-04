package earth.terrarium.pastel.deeper_down;

class InterpolationQueue<T> {
    private T current, last;

    public void accept(T newHead) {
        if (!ready()) {
            initialize(newHead);
            return;
        }

        last = current;
        current = newHead;
    }

    public void set(T current, T last) {
        accept(current);
        this.last = last;
    }

    public void initialize(T value) {
        last = value;
        current = value;
    }

    public T current() {
        return current;
    }

    public T last() {
        return last;
    }

    public boolean ready() {
        return current!=null && last!=null;
    }
}
