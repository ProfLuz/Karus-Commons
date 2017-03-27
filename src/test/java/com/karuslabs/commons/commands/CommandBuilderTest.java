/*
 * Copyright (C) 2017 Karus Labs
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.karuslabs.commons.commands;

import com.karuslabs.commons.commands.executors.CommandExecutor;

import java.util.Arrays;

import org.bukkit.plugin.Plugin;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


public class CommandBuilderTest {
    
    private CommandBuilder builder;
    private Plugin plugin;
    
    
    public CommandBuilderTest() {
        plugin = mock(Plugin.class);
        builder = new CommandBuilder(plugin);
    }
    
    
    @Test
    public void get() {
        CommandExecutor executor = mock(CommandExecutor.class);
        TabCompleter completer = mock(TabCompleter.class);
        
        Command command = builder.command("command")
                .description("description").aliases(Arrays.asList("cmd"))
                .permission("permission").message("message")
                .usage("usage").label("label")
                .executor(executor).tabCompleter(completer)
                .nestedCommand("name", mock(Command.class)).build();
        
        assertEquals("command", command.getName());
        assertEquals("description", command.getDescription());
        assertEquals(Arrays.asList("cmd"), command.getAliases());
        assertEquals("permission", command.getPermission());
        assertEquals("message", command.getPermissionMessage());
        assertEquals("usage", command.getUsage());
        assertEquals("label", command.getLabel());
        
        assertEquals(executor, command.getExecutor());
        assertEquals(completer, command.getTabCompleter());
        assertTrue(command.getNestedCommands().containsKey("name"));
    }
    
}