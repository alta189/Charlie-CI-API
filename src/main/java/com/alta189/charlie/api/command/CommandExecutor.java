package com.alta189.charlie.api.command;

public interface CommandExecutor {
	public CommandResult processCommand(Command command, CommandContext context) throws CommandException;
}
