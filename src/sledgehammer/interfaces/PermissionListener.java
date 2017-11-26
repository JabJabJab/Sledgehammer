package sledgehammer.interfaces;

import sledgehammer.lua.core.Player;

/*
This file is part of Sledgehammer.

   Sledgehammer is free software: you can redistribute it and/or modify
   it under the terms of the GNU Lesser General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.

   Sledgehammer is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU Lesser General Public License for more details.

   You should have received a copy of the GNU Lesser General Public License
   along with Sledgehammer. If not, see <http://www.gnu.org/licenses/>.
*/

/**
 * Interface for future alternative <Module> implementations for permissions.
 * This is to keep the internal design of the default permissions module clean
 * and support open alternatives for handling permissions.
 * 
 * @author Jab
 */
public interface PermissionListener {

	/**
	 * (Interface Method from <PermissionListener>)
	 * 
	 * This method returns whether or not a <Player> is authorized for a given
	 * <String> permission node.
	 * 
	 * @param player
	 *            The <Player> being tested.
	 * @param node
	 *            The <String> node being tested.
	 * @return Returns true if the <Player> is authorized with the given <String>
	 *         node.
	 */
	public boolean hasPermission(Player player, String node);

	/**
	 * (Interface Method from <PermissionListener>)
	 * 
	 * This method sets a permission for whether or not a <Player> is authorized for
	 * a given <String> permission node.
	 * 
	 * @param player
	 *            The <Player> the permission is being set.
	 * @param node
	 *            The <String> node being set.
	 * @param flag
	 *            The flag for the node to be explicitly authorized or denied.
	 */
	public void setPermission(Player player, String node, boolean flag);

	/**
	 * Sets a default permission flag.
	 * 
	 * (This is useful for Modules with dynamic commands that may need to be granted
	 * for default players)
	 * 
	 * @param node
	 *            The <String> permission node being set.
	 * @param flag
	 *            The flag to set for the permission node.
	 */
	public void addDefaultPermission(String node, boolean flag);

	/**
	 * @param node
	 *            The <String> permission node being checked.
	 * @return Returns true if the permission node is granted by default.
	 */
	public boolean hasDefaultPermission(String node);
}