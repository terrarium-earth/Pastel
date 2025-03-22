package de.dafuqs.spectrum.render.animation;

import org.jetbrains.annotations.*;

import java.lang.reflect.*;
import java.util.*;

public final class DataSignature<N extends Number> {
	
	@SuppressWarnings("rawtypes")
	public static final DataSignature DUMMY = new DataSignature(null, null, null, null, null, Collections.EMPTY_MAP);
	
	private final Field reference;
	final FlowHandler<N> handler;
	final Interpolation interpolation;
	final Map<FlowState, KeyFrame<N>> stateHolder;
	final N initialValue;
	final KeyFrame<N> defaultKeyFrame;
	
	DataSignature(Field reference, FlowHandler<N> handler, Interpolation interpolation, N initialValue, @Nullable KeyFrame<N> defaultKeyFrame, Map<FlowState, KeyFrame<N>> holderData) {
		this.reference = reference;
		this.handler = handler;
		this.interpolation = interpolation;
		this.initialValue = initialValue;
		this.stateHolder = Collections.unmodifiableMap(holderData);
		this.defaultKeyFrame = Objects.requireNonNullElseGet(defaultKeyFrame, () -> KeyFrame.simple(initialValue));
		
	}
	
	FlowData<N> instantiate() {
		var data = handler.createData(this);
		for (FlowState flowState : stateHolder.keySet()) {
			data.addStateListener(flowState, stateHolder.get(flowState));
		}
		return data;
	}
	
	
	void link(FlowData<?> data, Object target) throws IllegalAccessException {
		if (reference.canAccess(target)) {
			reference.set(target, data);
			return;
		}
		reference.setAccessible(true);
		reference.set(target, data);
		reference.setAccessible(false);
	}
}
