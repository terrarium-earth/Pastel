package de.dafuqs.spectrum.registries;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

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
	
	public static class Contextual<T, D> {
		
		private HashMap<T, BiConsumer<T, D>> deferred = null;
		
		public void flush(D data) {
			deferred.forEach((value, consumer) -> consumer.accept(value, data));
			deferred = null;
		}
		
		public Stream<T> streamKeys() {
			return deferred.keySet().stream();
		}
		
		public T defer(T value, BiConsumer<T, D> callback) {
			if (deferred == null)
				deferred = new HashMap<>();
			deferred.put(value, callback);
			return value;
		}
		
	}
	
}
