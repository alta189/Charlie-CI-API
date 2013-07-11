/*
 * This file is part of Charlie CI API.
 *
 * Copyright (c) 2013, Stephen Williams (alta189) <http://charlie.alta189.com/>
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
package com.alta189.charlie.api.command;

import com.alta189.charlie.api.command.annotation.Injector;

public abstract class CommandManager {
	private final CommandMap commandMap = new CommandMap();

	public abstract CommandResult execute(String raw) throws CommandException;

	public abstract CommandResult execute(Command command, CommandContext context) throws CommandException;

	public abstract boolean isCommand(String command);

	public abstract void registerCommand(Command command);

	public abstract void registerCommands(Named owner, Class<?> clazz, Injector injector);

	public CommandMap getCommandMap() {
		return commandMap;
	}
}
