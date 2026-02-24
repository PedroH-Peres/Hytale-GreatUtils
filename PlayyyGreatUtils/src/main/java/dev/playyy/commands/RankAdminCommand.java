package dev.playyy.commands;

import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.CommandSender;
import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
import com.hypixel.hytale.server.core.entity.entities.Player;
import dev.playyy.config.ConfigManager;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

public class RankAdminCommand extends CommandBase {

    private final ConfigManager configManager;

    public RankAdminCommand(ConfigManager configManager) {
        super("rankupreload", "Reload the Rankup configs");
        this.configManager = configManager;
    }


    @Override
    protected void executeSync(@NonNullDecl CommandContext commandContext) {

        Player sender = commandContext.senderAs(Player.class);

        if (!sender.hasPermission("hystar.admin.rank")) {
            sender.sendMessage(Message.raw("§cVocê não tem permissão para usar este comando."));
        }

        sender.sendMessage(Message.raw("§e[HyStar] Recarregando configurações de rank..."));

        try {
            configManager.loadConfig();

            int totalRanks = configManager.getConfig().getRanks().size();
            sender.sendMessage(Message.raw("§a[HyStar] Sucesso! " + totalRanks + " ranks foram carregados."));

        } catch (Exception e) {
            sender.sendMessage(Message.raw("§c[HyStar] Ocorreu um erro ao recarregar o JSON. Verifique o console."));
            e.printStackTrace();
        }


        sender.sendMessage(Message.raw("§cUso correto: /rankadmin reload"));
    }
}