/**
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 */

package tntrun.commands.setup.selection;

import org.bukkit.entity.Player;

import tntrun.commands.setup.CommandHandlerInterface;
import tntrun.selectionget.PlayerSelection;

public class SetP1 implements CommandHandlerInterface {

	private PlayerSelection selection;
	
	public SetP1(PlayerSelection selection) {
		this.selection = selection;
	}

	@Override
	public boolean handleCommand(Player player, String[] args) {
		player.sendMessage("§7[§6TNTRun§7] §cThis system is disabled, use worldedit to create arena");
		/*
	    selection.setSelectionPoint1(player);
		
		player.sendMessage("§7[§6TNTRun§7] §7Point §61 §7has been set to §6X: §7" + Math.round(player.getLocation().getX()) + " §6Y: §7" + Math.round(player.getLocation().getY()) + " §6Z: §7" + Math.round(player.getLocation().getZ()));
		*/
		return true;
	}

	@Override
	public int getMinArgsLength() {
		return 0;
	}

}