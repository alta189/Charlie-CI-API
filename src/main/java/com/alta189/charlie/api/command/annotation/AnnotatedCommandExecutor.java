/*
 * This file is part of Charlie CI API.
 *
 * Copyright (c) ${project.inceptionYear}, Stephen Williams (alta189) <http://charlie.alta189.com/>
 * Charlie CI API is licensed under the GNU Lesser General Public License.
 *
 * Charlie CI API is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Charlie CI API is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
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
