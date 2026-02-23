package dev.playyy;

import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import dev.playyy.commands.ExampleCommand;
import dev.playyy.commands.RankupCommand;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;


import javax.annotation.Nonnull;

public class GreatUtils extends JavaPlugin {

    protected LuckPerms luckPerms;


    public GreatUtils(@Nonnull JavaPluginInit init) {
        super(init);
    }

    @Override
    protected void setup() {
        this.getCommandRegistry().registerCommand(new ExampleCommand("example", "An example command"));

    }

    @Override
    protected void start(){
        this.luckPerms = LuckPermsProvider.get();
        this.getCommandRegistry().registerCommand(new RankupCommand(luckPerms));
    }
}