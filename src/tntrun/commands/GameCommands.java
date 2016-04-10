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

package tntrun.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tntrun.arena.Arena;
import tntrun.datahandler.ArenasManager;
import tntrun.lobby.GlobalLobby;
import tntrun.messages.Messages;

public class GameCommands implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("A player is expected");
			return true;
		}
		Player player = (Player) sender;
		// handle commands
		// help command
		if (args.length == 1 && args[0].equalsIgnoreCase("help")) {
			sender.sendMessage("§a============§7[§6TNTRun§7]§a============");
			sender.sendMessage("§a/tr lobby §f- §cteleport to lobby");
			sender.sendMessage("§a/tr list §f- §clist all arenas");
			sender.sendMessage("§a/tr join {arena} §f- §cjoin arena");
			sender.sendMessage("§a/tr leave §f- §cleave current arena");
			sender.sendMessage("§a/tr vote §f- §cvote for current arena start");
			sender.sendMessage("§a/tr cmds §f- §cView all commands");
			return true;
		} else if (args.length == 1 && args[0].equalsIgnoreCase("lobby")) {
			if (GlobalLobby.getInstance().isLobbyLocationSet()) {
				if (GlobalLobby.getInstance().isLobbyLocationWorldAvailable()) {
					player.teleport(GlobalLobby.getInstance().getLobbyLocation());
					Messages.sendMessage(player, Messages.teleporttolobby);
				} else {
					player.sendMessage("Lobby world is unloaded, can't join lobby");
				}
			} else {
				sender.sendMessage("Lobby is not set");

			}
			return true;
		}
		// list arenas
		else if (args.length == 1 && args[0].equalsIgnoreCase("list")) {
			StringBuilder message = new StringBuilder(200);
			message.append(Messages.availablearenas);
			for (Arena arena : ArenasManager.getInstance().getArenas()) {
				if (arena.getStatusManager().isArenaEnabled()) {
					message.append("&a" + arena.getArenaName() + " ");
				} else {
					message.append("&c" + arena.getArenaName() + " ");
				}
			}
			Messages.sendMessage(player, message.toString());
			return true;
		}
		// join arena
		else if (args.length == 2 && args[0].equalsIgnoreCase("join")) {
			if (player.hasPermission("tntrun.onlysignjoin")) {
				player.sendMessage("You can join the game only by using a sign");
				return true;
			}
			Arena arena = ArenasManager.getInstance().getArenaByName(args[1]);
			if (arena != null) {
				boolean canJoin = arena.getPlayerHandler().checkJoin(player);
				if (canJoin) {
					arena.getPlayerHandler().spawnPlayer(player, Messages.playerjoinedtoplayer, Messages.playerjoinedtoothers);
				}
				return true;
			} else {
				sender.sendMessage("Arena does not exist");
				return true;
			}
		}
		// leave arena
		else if (args.length == 1 && args[0].equalsIgnoreCase("leave")) {
			Arena arena = ArenasManager.getInstance().getPlayerArena(player.getName());
			if (arena != null) {
				arena.getPlayerHandler().leavePlayer(player, Messages.playerlefttoplayer, Messages.playerlefttoothers);
				return true;
			} else {
				sender.sendMessage("You are not in arena");
				return true;
			}
		}
		// all commands
		else if (args.length == 1 && args[0].equalsIgnoreCase("cmds")) {
			sender.sendMessage("§a============§7[§6TNTRun§7]§a============");
			sender.sendMessage("§a/trsetup setlobby §f- §cSet lobby on your location");
			sender.sendMessage("§a/trsetup create {arena} §f- §cCreate new Arena");
			sender.sendMessage("§a/trsetup setarena {arena} §f- §cSet bounds for arena");
			sender.sendMessage("§a/trsetup setloselevel {arena} §f- §cSet looselevel bounds for arena");
			sender.sendMessage("§a/trsetup setspawn {arena} §f- §cSet spawn for players on your location");
			sender.sendMessage("§a/trsetup setspectate {arena} §f- §cSet spectators spawn");
			sender.sendMessage("§a/trsetup finish {arena} §f- §cFinish arena and save it to config file");
			sender.sendMessage("§a============§7[§6Other commands§7]§a============");
			sender.sendMessage("§a/trsetup delspectate {arena} §f- §cDelete spectators spawn");
			sender.sendMessage("§a/trsetup setgameleveldestroydelay {arena} {ticks} §f- §cSet a delay of removing blocks when player stepped on it");
			sender.sendMessage("§a/trsetup setmaxplayers {arena} {players} §f- §cSet a max players for arena");
			sender.sendMessage("§a/trsetup setminplayers {arena} {players} §f- §cSet a min players for arena");
			sender.sendMessage("§a/trsetup setvotepercent {arena} {0<votepercent<1} §f- §cSet a vote percent for arena  (Default: 0.75)");
			sender.sendMessage("§a/trsetup settimelimit {arena} {seconds} §f- §cSet a limit for arena");
			sender.sendMessage("§a/trsetup setcountdown {arena} {seconds} §f- §cSet a countdown for arena");
			sender.sendMessage("§a/trsetup setitemsrewards {arena} §f- §cSet a everithing to reward (Item)");
			sender.sendMessage("§a/trsetup setmoneyrewards {arena} {money} §f- §cSet a money reward for winning player");
			sender.sendMessage("§a/trsetup setteleport {arena} {previous/lobby} §f- §cSet teleport when you lose or win in arena");
			sender.sendMessage("§a/trsetup setdamage {arena} {on/off/zero} §f- §cSet a pvp for arena");
			sender.sendMessage("§a/trsetup reloadbars §f- §cReload Bar messages");
			sender.sendMessage("§a/trsetup reloadmsg §f- §cReload arena messages");
			sender.sendMessage("§a/trsetup enable {arena} §f- §cEnable Arena");
			sender.sendMessage("§a/trsetup disable {arena} §f- §cDisable Arena");
			sender.sendMessage("§a/trsetup delete {arena} §f- §cDelete Arena");
		}
		// vote
		else if (args.length == 1 && args[0].equalsIgnoreCase("vote")) {
			Arena arena = ArenasManager.getInstance().getPlayerArena(player.getName());
			if (arena != null) {
				if (arena.getPlayerHandler().vote(player)) {
					Messages.sendMessage(player, Messages.playervotedforstart);
				} else {
					Messages.sendMessage(player, Messages.playeralreadyvotedforstart);
				}
				return true;
			} else {
				sender.sendMessage("You are not in arena");
				return true;
			}
		}
		return false;
	}

}
