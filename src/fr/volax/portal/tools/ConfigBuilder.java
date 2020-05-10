/*
 * PortalsCanceller, a Minecraft portals manipulation toolkit
 * Copyright (C) Volax <http://volax.fr>
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package fr.volax.portal.tools;

import fr.volax.portal.MainPortal;

public class ConfigBuilder {

    /**
     * Return un String dans la config principal
     * @param value La direction du string à get
     * @return Contenue de "value" dans la config
     */
    public static String getString(String value){
        return MainPortal.getMain().getConfig().getString(value).replaceAll("&","§").replaceAll("%prefix%", MainPortal.getMain().PREFIX);
    }

    /**
     * Return un int dans la config principal
     * @param value La direction du int à get
     * @return Contenue de "value" dans la config
     */
    public static int getInt(String value){
        return MainPortal.getMain().getConfig().getInt(value);
    }

    /**
     * Return un Boolean dans la config principal
     * @param value La direction du boolean à get
     * @return Contenue de "value" dans la config
     */
    public static boolean getBoolean(String value){
        return MainPortal.getMain().getConfig().getBoolean(value);
    }

    /**
     * Modifier une donnée dans la config principale
     * @param value La direction de la donnée à modifier
     * @param data Donnée à modifier dans la config
     */
    public static void set(String value, String data){
        MainPortal.getMain().getConfig().set(value, data);
        MainPortal.getMain().saveConfig();
        MainPortal.getMain().reloadConfig();
    }
}
