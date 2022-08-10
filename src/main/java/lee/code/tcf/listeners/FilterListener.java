package lee.code.tcf.listeners;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import lee.code.tcf.TabCompleteFilter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandSendEvent;

public class FilterListener implements Listener {

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onPlayerCommandSendEvent(PlayerCommandSendEvent e) {
        TabCompleteFilter plugin = TabCompleteFilter.getPlugin();

        Player player = e.getPlayer();
        if (!player.isOp() && !player.hasPermission("mineblock.bypass.tabcompletefilter")) { // MineBlock
            for (String group : plugin.getData().getGroups()) {
                if (player.hasPermission("rank." + group)) {
                    List<String> whitelist = plugin.getData().getGroupList(group);
                    if (!whitelist.isEmpty()) {

                        if (!plugin.getConfig().getBoolean("config.use-as-blacklist", true)) { // MineBlock
                            e.getCommands().retainAll(whitelist);
                        } else { // MineBlock
                            Collection<String> results = new ArrayList<>();
                            for (String cmd : e.getCommands()) { // MineBlock
                                for (String command : whitelist) {
                                    if (cmd.contains(command)) { // MineBlock
                                        results.add(cmd); // MineBlock
                                        break;
                                    }
                                }
                            }
                            e.getCommands().removeAll(results);
                        }
                    }
                }
            }
        }
    }
}
