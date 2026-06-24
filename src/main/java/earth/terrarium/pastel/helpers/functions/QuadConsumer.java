package earth.terrarium.pastel.helpers.functions;

public interface QuadConsumer<T, U, V, W> {
    void accept(T t, U u, V v, W w);
}
