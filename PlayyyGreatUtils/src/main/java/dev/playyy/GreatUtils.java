package dev.playyy;

import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import dev.playyy.commands.ExampleCommand;
import dev.playyy.commands.RankAdminCommand;
import dev.playyy.commands.RankListCommand;
import dev.playyy.commands.RankupCommand;
import dev.playyy.config.ConfigManager;
import dev.playyy.config.RankConfig;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;


import javax.annotation.Nonnull;
import java.io.File;

public class GreatUtils extends JavaPlugin {

    protected LuckPerms luckPerms;
    private ConfigManager rankConfigManager;

    public GreatUtils(@Nonnull JavaPluginInit init) {
        super(init);
    }

    @Override
    protected void setup() {
        this.getCommandRegistry().registerCommand(new ExampleCommand("example", "An example command"));
        File pluginFolder = new File("mods/playyy_greatutils");

        rankConfigManager = new ConfigManager(pluginFolder);
        rankConfigManager.loadConfig();

        RankConfig ranksAtuais = rankConfigManager.getConfig();
        this.getCommandRegistry().registerCommand(new RankAdminCommand(rankConfigManager));
        this.getCommandRegistry().registerCommand(new RankListCommand(rankConfigManager));

    }

    @Override
    protected void start(){
        this.luckPerms = LuckPermsProvider.get();
        this.getCommandRegistry().registerCommand(new RankupCommand(luckPerms));
    }
}