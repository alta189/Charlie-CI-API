package com.alta189.charlie.api.command;

import java.util.ArrayList;
import java.util.List;

public class Command {
	private final Named owner;
	private final String command;
	private String prefix;
	private final List<String> aliases = new ArrayList<String>();
	private CommandExecutor executor;
	private String desc;
	private String usage;

	public Command(Named owner, String command) {
		this.owner = owner;
		this.command = command.toLowerCase();
	}

	public Named getOwner() {
		return owner;
	}

	public String getCommand() {
		return command;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public List<String> getAliases() {
		return aliases;
	}

	public CommandExecutor getExecutor() {
		return executor;
	}

	public void setExecutor(CommandExecutor executor) {
		this.executor = executor;
	}

	public String getDescription() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getUsage() {
		return usage;
	}

	public void setUsage(String usage) {
		this.usage = usage;
	}
}
