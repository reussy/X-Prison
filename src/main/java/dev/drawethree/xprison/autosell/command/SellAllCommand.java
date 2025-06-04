package dev.drawethree.xprison.autosell.command;

import dev.drawethree.xprison.autosell.XPrisonAutoSell;
import dev.drawethree.xprison.utils.misc.RegionUtils;
import dev.drawethree.xprison.utils.player.PlayerUtils;
import me.lucko.helper.Commands;
import me.lucko.helper.command.context.CommandContext;
import org.bukkit.entity.Player;
import org.codemc.worldguardwrapper.WorldGuardWrapper;
import org.codemc.worldguardwrapper.region.IWrappedRegion;
import org.jetbrains.annotations.NotNull;

public class SellAllCommand {

    private static final String COMMAND_NAME = "sellall";
    private final XPrisonAutoSell plugin;

    public SellAllCommand(XPrisonAutoSell plugin) {
        this.plugin = plugin;
    }

    public void register() {
        Commands.create()
                .assertPlayer()
                .handler(c -> {
                    IWrappedRegion region;
                    if (plugin.getCore().getAutoSell().getAutoSellConfig().getYamlConfig().getBoolean("use-regions")) {
                        region = this.parseRegionFromCommandContext(c);

                        if (region == null) {
                            PlayerUtils.sendMessage(c.sender(), this.plugin.getAutoSellConfig().getMessage("invalid_region"));
                            return;
                        }
                    } else {
                        region = RegionUtils.getRegionWithHighestPriority(c.sender().getLocation());
                    }

                    this.plugin.getManager().sellAll(c.sender(), region);

                }).registerAndBind(this.plugin.getCore(), COMMAND_NAME);
    }

    private IWrappedRegion parseRegionFromCommandContext(@NotNull CommandContext<Player> c) {
        IWrappedRegion region = null;
        if (c.args().isEmpty()) {
            region = RegionUtils.getRegionWithHighestPriority(c.sender().getLocation());
        } else if (c.args().size() == 1) {
            String regionName = c.rawArg(0);
            region = WorldGuardWrapper.getInstance().getRegion(c.sender().getLocation().getWorld(), regionName).orElse(null);
        }
        return region;
    }
}
