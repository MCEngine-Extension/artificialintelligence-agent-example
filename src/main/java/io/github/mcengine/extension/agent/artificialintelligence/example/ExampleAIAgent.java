package io.github.mcengine.extension.agent.artificialintelligence.example;

import io.github.mcengine.api.core.MCEngineCoreApi;
import io.github.mcengine.api.core.extension.logger.MCEngineExtensionLogger;
import io.github.mcengine.api.artificialintelligence.extension.agent.IMCEngineArtificialIntelligenceAgent;

import io.github.mcengine.extension.agent.artificialintelligence.example.command.AIAgentCommand;
import io.github.mcengine.extension.agent.artificialintelligence.example.listener.AIAgentListener;
import io.github.mcengine.extension.agent.artificialintelligence.example.tabcompleter.AIAgentTabCompleter;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Main class for the Artificial Intelligence <b>Agent</b> example module.
 * <p>
 * Registers the {@code /aiagentexample} command and related event listeners.
 * This class demonstrates how to wire up commands and listeners while integrating
 * with the {@link IMCEngineArtificialIntelligenceAgent} extension lifecycle.
 */
public class ExampleAIAgent implements IMCEngineArtificialIntelligenceAgent {

    /** Custom extension logger for this module, with contextual labeling. */
    private MCEngineExtensionLogger logger;

    /**
     * Initializes the AI Agent example module.
     * Called automatically by the MCEngine core plugin.
     *
     * @param plugin The Bukkit plugin instance.
     */
    @Override
    public void onLoad(Plugin plugin) {
        // Initialize contextual logger once and keep it for later use.
        this.logger = new MCEngineExtensionLogger(plugin, "Agent", "ArtificialIntelligenceExampleAgent");

        try {
            // Register event listener
            PluginManager pluginManager = Bukkit.getPluginManager();
            pluginManager.registerEvents(new AIAgentListener(plugin, this.logger), plugin);

            // Reflectively access Bukkit's CommandMap
            Field commandMapField = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            commandMapField.setAccessible(true);
            CommandMap commandMap = (CommandMap) commandMapField.get(Bukkit.getServer());

            // Define the /aiagentexample command
            Command aiAgentExampleCommand = new Command("aiagentexample") {

                /** Handles command execution for /aiagentexample. */
                private final AIAgentCommand handler = new AIAgentCommand();

                /** Handles tab-completion for /aiagentexample. */
                private final AIAgentTabCompleter completer = new AIAgentTabCompleter();

                @Override
                public boolean execute(CommandSender sender, String label, String[] args) {
                    return handler.onCommand(sender, this, label, args);
                }

                @Override
                public List<String> tabComplete(CommandSender sender, String alias, String[] args) {
                    return completer.onTabComplete(sender, this, alias, args);
                }
            };

            aiAgentExampleCommand.setDescription("Artificial Intelligence Agent example command.");
            aiAgentExampleCommand.setUsage("/aiagentexample");

            // Dynamically register the /aiagentexample command
            commandMap.register(plugin.getName().toLowerCase(), aiAgentExampleCommand);

            this.logger.info("Enabled successfully.");
        } catch (Exception e) {
            this.logger.warning("Failed to initialize ExampleArtificialIntelligenceAgent: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Called when the AI Agent example module is disabled/unloaded.
     *
     * @param plugin The Bukkit plugin instance.
     */
    @Override
    public void onDisload(Plugin plugin) {
        if (this.logger != null) {
            this.logger.info("Disabled.");
        }
    }

    /**
     * Sets the unique ID for this module.
     *
     * @param id the assigned identifier (ignored; a fixed ID is used for consistency)
     */
    @Override
    public void setId(String id) {
        MCEngineCoreApi.setId("mcengine-artificialintelligence-agent-example");
    }
}
