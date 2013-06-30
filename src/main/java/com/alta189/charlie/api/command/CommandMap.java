package com.alta189.charlie.api.command;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CommandMap {
	private final Map<String, Command> commandMap = new HashMap<String, Command>();
	private final Map<String, Command> aliasMap = new HashMap<String, Command>();

	public void addCommand(Command command) {
		if (commandMap.get(command.getCommand()) == null) {
			commandMap.put(command.getCommand(), command);
			for (String alias : command.getAliases()) {
				registerAlias(alias, command);
			}
		}
	}

	private void registerAlias(String alias, Command command) {
		if (aliasMap.get(alias.toLowerCase()) == null) {
			aliasMap.put(alias.toLowerCase(), command);
		}
	}

	public Command getCommand(String cmd) {
		return getCommand(cmd, false);
	}

	public Command getCommand(String cmd, boolean ignoreAliases) {
		Command command = commandMap.get(cmd.toLowerCase());
		if (command == null && !ignoreAliases) {
			command = aliasMap.get(cmd.toLowerCase());
		}
		return command;
	}

	public Collection<Command> getCommands() {
		return commandMap.values();
	}
}
