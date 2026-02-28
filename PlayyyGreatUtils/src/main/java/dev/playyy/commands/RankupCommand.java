package dev.playyy.commands;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.inventory.ItemStack;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import dev.playyy.config.ConfigManager;
import dev.playyy.config.PlayerDataManager;
import dev.playyy.config.RankConfig;
import dev.playyy.models.ItemCost;
import dev.playyy.models.Rank;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.types.InheritanceNode;
import net.luckperms.api.platform.PlayerAdapter;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class RankupCommand extends AbstractPlayerCommand {

    private final LuckPerms luckPerms;
    private final PlayerDataManager playerDataManager;
    private final ConfigManager configManager;

    public RankupCommand(LuckPerms luckPerms, PlayerDataManager playerDataManager, ConfigManager configManager) {
        super("rankup", "Rankup Command");
        this.luckPerms = luckPerms;
        this.playerDataManager = playerDataManager;
        this.configManager = configManager;
    }

    @Override
    protected void execute(@NonNullDecl CommandContext commandContext, @NonNullDecl Store<EntityStore> store, @NonNullDecl Ref<EntityStore> ref, @NonNullDecl PlayerRef playerRef, @NonNullDecl World world) {
        PlayerAdapter<PlayerRef> playerAdapter = LuckPermsProvider.get().getPlayerAdapter(PlayerRef.class);
        User user = playerAdapter.getUser(playerRef);
        processarRankup(playerRef.getUuid());
    }

    public void processarRankup(UUID playerUuid) {
        PlayerRef playerRef = Universe.get().getPlayer(playerUuid);
        if (playerRef==null) return;
        Player player = playerRef.getReference().getStore().getComponent(playerRef.getReference(), Player.getComponentType());

        String rankId = playerDataManager.getPlayerRank(playerUuid.toString());
        Rank rank = configManager.getConfig().getRankById(rankId);
        if(rank.isMaxRank()){
            player.sendMessage(Message.raw("Voce ja esta no ultimo rank!"));
            return;
        }
        Rank newRank = configManager.getConfig().getRankById(rank.getNextRank());
        String newGroup = newRank.getPermissionGroup();

        double valueCost = newRank.getCoinCost();
        List<ItemCost> itemsCost = newRank.getItemCosts();
        Map<String, Integer> mapItemQtt = new ConcurrentHashMap<>();
        Map<String, Boolean> mapHasTheQuantity = new ConcurrentHashMap<>();
        boolean haveAllItems = false;
        for(var item : itemsCost){
            mapItemQtt.put(item.getItemId(), 0);
        }

        for(int i = 0; i < player.getInventory().getCombinedHotbarFirst().getCapacity(); i++){
            ItemStack itemStack = player.getInventory().getCombinedHotbarFirst().getItemStack((short) i);
            if(itemStack == null) continue;
            if(mapItemQtt.containsKey(itemStack.getItemId())){
                mapItemQtt.compute(itemStack.getItemId(), (k, actualQuantity) -> actualQuantity + itemStack.getQuantity());
            }
        }
        for(var item : itemsCost){
            if(mapItemQtt.get(item.getItemId()) >= item.getAmount()){
                mapHasTheQuantity.put(item.getItemId(), true);
            }else{
                mapHasTheQuantity.put(item.getItemId(), false);
            }
        }
        haveAllItems = !mapHasTheQuantity.containsValue(false);
        if(haveAllItems){
            for(var item : itemsCost){
                ItemStack itemStack = new ItemStack(item.getItemId(), item.getAmount());
                player.getInventory().getCombinedHotbarFirst().removeItemStack(itemStack);
            }
        }else{
            player.sendMessage(Message.raw("Voce nao possui os itens necessarios!"));
            return;
        }

        luckPerms.getUserManager().modifyUser(playerUuid, user -> {
            InheritanceNode nodeNovo = InheritanceNode.builder(newGroup).build();
            InheritanceNode nodeAntigo = InheritanceNode.builder(rank.getPermissionGroup()).build();
            user.data().add(nodeNovo);
            user.data().remove(nodeAntigo);
            user.setPrimaryGroup(newGroup);
            playerDataManager.setPlayerRank(playerUuid.toString(), newRank.getId());
            playerDataManager.save();
            System.out.println("Player " + playerUuid + " promovido para " + newGroup);
        });
    }
}
