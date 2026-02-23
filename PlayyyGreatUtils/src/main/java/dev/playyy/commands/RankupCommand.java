package dev.playyy.commands;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.types.InheritanceNode;
import net.luckperms.api.platform.PlayerAdapter;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

import java.util.UUID;

public class RankupCommand extends AbstractPlayerCommand {

    private final LuckPerms luckPerms;

    public RankupCommand(LuckPerms luckPerms) {
        super("rankup", "Rankup Command");
        this.luckPerms = luckPerms;
    }

    @Override
    protected void execute(@NonNullDecl CommandContext commandContext, @NonNullDecl Store<EntityStore> store, @NonNullDecl Ref<EntityStore> ref, @NonNullDecl PlayerRef playerRef, @NonNullDecl World world) {
        PlayerAdapter<PlayerRef> playerAdapter = LuckPermsProvider.get().getPlayerAdapter(PlayerRef.class);
        User user = playerAdapter.getUser(playerRef);
        processarRankup(playerRef.getUuid(), "vipsupernova");
    }

    public void processarRankup(UUID playerUuid, String novoGrupo) {
        luckPerms.getUserManager().modifyUser(playerUuid, user -> {

            InheritanceNode nodeNovo = InheritanceNode.builder(novoGrupo).build();

            user.data().add(nodeNovo);

            user.setPrimaryGroup(novoGrupo);

            System.out.println("Player " + playerUuid + " promovido para " + novoGrupo);
        });
    }
}
