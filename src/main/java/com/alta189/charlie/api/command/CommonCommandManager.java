package com.alta189.charlie.api.command;

import com.alta189.charlie.api.command.annotation.AnnotatedCommandFactory;
import com.alta189.charlie.api.command.annotation.Injector;
import com.alta189.charlie.api.logging.console.Console;

import java.util.List;

import jline.console.completer.Completer;
import jline.console.completer.StringsCompleter;

public class CommonCommandManager extends CommandManager {
	private final Console console;

	public CommonCommandManager(Console console) {
		this.console = console;
	}

	@Override
	public CommandResult execute(Command command, CommandContext context) throws CommandException {
		CommandExecutor executor = command.getExecutor();
		if (executor != null) {
			return executor.processCommand(command, context);
		}
		return null;
	}

	@Override
	public CommandResult execute(String raw) throws CommandException {
		Command command = getCommandMap().getCommand(getCommand(raw));
		if (command != null) {
			String[] args = getArgs(raw);

			CommandContext context = new CommandContext(args);
			return execute(command, context);
		}
		return null;
	}

	@Override
	public boolean isCommand(String command) {
		command = getCommand(command);
		Command cmd = getCommandMap().getCommand(command);
		return cmd != null;
	}

	@Override
	public void registerCommand(Command command) {
		getCommandMap().addCommand(command);
		console.getReader().addCompleter(new StringsCompleter(command.getCommand()));
	}

	@Override
	public void registerCommands(Named owner, Class<?> clazz, Injector injector) {
		AnnotatedCommandFactory factory = new AnnotatedCommandFactory(injector);
		List<Command> commands = factory.createCommands(owner, clazz);
		if (commands != null && commands.size() > 0) {
			for (Command command : commands) {
				registerCommand(command);
			}
		}
	}

	public String[] getArgs(String raw) {
		if (!raw.contains(" ") || raw.equals(" ")) {
			return null;
		}

		int firstSpace = raw.indexOf(" ");
		if (firstSpace + 1 >= raw.length()) {
			return null;
		}
		return raw.substring(firstSpace + 1).split(" ");
	}

	public String getCommand(String raw) {
		if (raw.contains(" ")) {
			return raw.substring(0, raw.indexOf(" "));
		}
		return raw;
	}
}
