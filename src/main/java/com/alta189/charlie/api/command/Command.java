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
