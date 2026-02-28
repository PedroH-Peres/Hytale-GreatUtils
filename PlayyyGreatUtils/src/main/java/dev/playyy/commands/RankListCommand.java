package dev.playyy.commands;

import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
import com.hypixel.hytale.server.core.entity.entities.Player;
import dev.playyy.config.ConfigManager;
import dev.playyy.models.Rank;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

import java.util.Map;

public class RankListCommand extends CommandBase {

    private final ConfigManager configManager;

    public RankListCommand(ConfigManager configManager) {
        super("ranklist", "Lista todos os ranks configurados no servidor");
        this.configManager = configManager;
    }

    @Override
    protected void executeSync(@NonNullDecl CommandContext commandContext) {

        Player sender = commandContext.senderAs(Player.class);

        if (configManager.getConfig() == null || configManager.getConfig().getRanks() == null || configManager.getConfig().getRanks().isEmpty()) {
            sender.sendMessage(Message.raw("§c[HyStar] Nenhum rank foi encontrado ou carregado no sistema."));
            return;
        }

        Map<String, Rank> ranks = configManager.getConfig().getRanks();
        int totalRanks = ranks.size();

        sender.sendMessage(Message.raw("[HyStar] Ranks Disponíveis (" + totalRanks + ")"));

        for (Rank rank : ranks.values()) {

            StringBuilder info = new StringBuilder();
            info.append("- ").append(rank.getName())
                    .append(" | Custo: ").append(rank.getCoinCost()).append(" coins");

            if (rank.requiresItems()) {
                info.append(" + Itens");
            }

            if (rank.isMaxRank()) {
                info.append(" [MÁXIMO]");
            }

            sender.sendMessage(Message.raw(info.toString()));
        }

        sender.sendMessage(Message.raw("-----------------------------------"));
    }
}