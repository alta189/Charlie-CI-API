package com.alta189.charlie.api.command.annotation;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.alta189.charlie.api.command.CommandResult;
import com.alta189.charlie.api.command.Named;

public class AnnotatedCommandFactory {
	private final Injector injector;

	public AnnotatedCommandFactory(Injector injector) {
		this.injector = injector;
	}

	public List<com.alta189.charlie.api.command.Command> createCommands(Named owner, Class<?> clazz) {
		List<com.alta189.charlie.api.command.Command> result = new ArrayList<com.alta189.charlie.api.command.Command>();
		Object instance = null;
		if (injector != null) {
			instance = injector.newInstance(clazz);
		}

		for (Method method : clazz.getMethods()) {
			if (!Modifier.isStatic(method.getModifiers()) && instance == null) {
				continue;
			}
			if (!method.isAnnotationPresent(Command.class)) {
				continue;
			}
			Command command = method.getAnnotation(Command.class);
			if (command.name() == null) {
				continue;
			}
			if (!method.getReturnType().equals(CommandResult.class)) {
				continue;
			}
			com.alta189.charlie.api.command.Command cmd = new com.alta189.charlie.api.command.Command(owner, command.name());
			cmd.getAliases().addAll(Arrays.asList(command.aliases()));
			cmd.setDesc(command.desc());
			cmd.setExecutor(new AnnotatedCommandExecutor(instance, method));

			if (method.isAnnotationPresent(Usage.class)) {
				Usage usage = method.getAnnotation(Usage.class);
				cmd.setUsage(usage.value());
			}

			result.add(cmd);
		}
		return result;
	}
}
