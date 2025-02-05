package de.dafuqs.spectrum.registries;

import java.util.*;
import java.util.function.*;

public class Deferrer {
	
	private final ArrayList<Runnable> deferred = new ArrayList<>();
	
	public void flush() {
		deferred.forEach(Runnable::run);
		deferred.clear();
		deferred.trimToSize();
	}
	
	public <T> T defer(T value, Consumer<T> callback) {
		deferred.add(() -> callback.accept(value));
		return value;
	}
	
	public static <T> Chain<T> chain(T value) {
		return new Chain<>(value);
	}
	
	public record Chain<T>(T value) {
		
		public Chain<T> defer(Deferrer deferrer, Consumer<T> callback) {
			deferrer.defer(value, callback);
			return this;
		}
		
		public <D> Chain<T> defer(Contextual<D> deferrer, BiConsumer<T, D> callback) {
			deferrer.defer(value, callback);
			return this;
		}
		
	}
	
	public static class Contextual<D> {
		
		private final ArrayList<Consumer<D>> deferred = new ArrayList<>();
		
		public void flush(D data) {
			deferred.forEach(c -> c.accept(data));
			deferred.clear();
			deferred.trimToSize();
		}
		
		public <T> T defer(T value, BiConsumer<T, D> callback) {
			deferred.add(data -> callback.accept(value, data));
			return value;
		}
		
	}
	
}
