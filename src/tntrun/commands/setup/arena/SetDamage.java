package tntrun.commands.setup.arena;

import org.bukkit.entity.Player;

import tntrun.arena.Arena;
import tntrun.arena.structure.StructureManager.DamageEnabled;
import tntrun.commands.setup.CommandHandlerInterface;
import tntrun.datahandler.ArenasManager;

public class SetDamage implements CommandHandlerInterface {

	@Override
	public boolean handleCommand(Player player, String[] args) {
		Arena arena = ArenasManager.getInstance().getArenaByName(args[0]);
		if (arena != null) {
			if (arena.getStatusManager().isArenaEnabled()) {
				player.sendMessage("Disable arena first");
				return true;
			}
			if (args[1].equals("yes")) {
				arena.getStructureManager().setDamageEnabled(DamageEnabled.YES);
			} else if (args[1].equals("no")) {
				arena.getStructureManager().setDamageEnabled(DamageEnabled.NO);
			} else if (args[1].equals("zero")) {
				arena.getStructureManager().setDamageEnabled(DamageEnabled.ZERO);
			}
			player.sendMessage("Damage enabled set");
		} else {
			player.sendMessage("Arena does not exist");
		}
		return true;
	}

	@Override
	public int getMinArgsLength() {
		return 2;
	}

}
