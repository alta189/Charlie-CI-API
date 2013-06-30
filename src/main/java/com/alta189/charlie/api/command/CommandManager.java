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
