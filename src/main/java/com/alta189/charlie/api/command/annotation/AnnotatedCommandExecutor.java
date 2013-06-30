package com.alta189.charlie.api.command.annotation;

import com.alta189.charlie.api.command.Command;
import com.alta189.charlie.api.command.CommandContext;
import com.alta189.charlie.api.command.CommandException;
import com.alta189.charlie.api.command.CommandExecutor;
import com.alta189.charlie.api.command.CommandResult;
import com.alta189.charlie.api.command.WrappedCommandException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class AnnotatedCommandExecutor implements CommandExecutor {
	private final Object instance;
	private final Method method;

	public AnnotatedCommandExecutor(Object instance, Method method) {
		this.instance = instance;
		this.method = method;
	}

	public CommandResult processCommand(Command command, CommandContext args) throws CommandException {
		try {
			List<Object> commandArgs = new ArrayList<Object>(3);
			commandArgs.add(args);
			return (CommandResult) method.invoke(instance, commandArgs.toArray());
		} catch (IllegalAccessException e) {
			throw new WrappedCommandException(e);
		} catch (InvocationTargetException e) {
			if (e.getCause() == null) {
				throw new WrappedCommandException(e);
			} else {
				Throwable cause = e.getCause();
				if (cause instanceof CommandException) {
					throw (CommandException) cause;
				} else {
					throw new WrappedCommandException(cause);
				}
			}
		} catch (Exception e) {
			throw new WrappedCommandException(e);
		}
	}
}
