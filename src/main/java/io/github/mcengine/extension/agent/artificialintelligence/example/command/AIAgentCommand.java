package io.github.mcengine.extension.agent.artificialintelligence.example.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Handles the {@code /aiagentexample} command logic.
 */
public class AIAgentCommand implements CommandExecutor {

    /**
     * Executes the {@code /aiagentexample} command.
     *
     * @param sender  The source of the command.
     * @param command The command that was executed.
     * @param label   The alias used.
     * @param args    The command arguments.
     * @return {@code true} if the command was handled successfully.
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage("§aAIAgent example command executed!");
        return true;
    }
}
