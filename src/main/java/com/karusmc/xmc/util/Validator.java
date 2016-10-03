/*
 * Copyright (C) 2016 PanteLegacy @ karusmc.com
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
package com.karusmc.xmc.util;

import com.karusmc.xmc.core.*;

import org.bukkit.command.*;
import org.bukkit.entity.Player;

/**
 *
 * @author PanteLegacy @ karusmc.com
 */
public class Validator {
    
    
    public static boolean isValid(boolean criteria, Else handle, CommandSender sender) {
        if (!criteria) {
            handle.handle(sender);
        }
        
        return criteria;
    }
    
    
    
    public static boolean isAllowed(CommandSender sender, XMCommand command) {
        return (command.isConsoleAllowed() || !(sender instanceof ConsoleCommandSender)) && command.testPermissionSilent(sender);
    }
    
    
    public static boolean hasLength(int min, int length, int max) {
        return min <= length && length >= max;
    }
    
    
    public static boolean hasCooldownLeft(ConfigurableCommand command, long lastUse, long currentTime) {
        return currentTime - lastUse < command.getCooldown();
    }
    
    
    public static boolean canUseInWorld(CommandSender sender, ConfigurableCommand command) {
        return !(sender instanceof Player)
                || (command.getWorlds().contains(((Player) sender).getWorld().getName()) && command.getListType() == ListType.WHITELIST);
    }
    
}