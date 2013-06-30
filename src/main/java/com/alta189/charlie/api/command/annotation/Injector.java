package com.alta189.charlie.api.command.annotation;

public interface Injector {
	public Object newInstance(Class<?> clazz);
}
