package com.alta189.charlie.api.command.annotation;

public class EmptyConstructorInjector implements Injector {
	private static final EmptyConstructorInjector instance = new EmptyConstructorInjector();

	public static EmptyConstructorInjector getInstance() {
		return instance;
	}

	@Override
	public Object newInstance(Class<?> clazz) {
		try {
			return clazz.newInstance();
		} catch (InstantiationException e) {
			return null;
		} catch (IllegalAccessException e) {
			return null;
		}
	}
}
