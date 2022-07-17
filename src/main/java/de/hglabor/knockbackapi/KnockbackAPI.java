package de.hglabor.knockbackapi;

import de.hglabor.knockbackapi.config.KnockbackAPIConfiguration;
import de.hglabor.knockbackapi.listener.EntityKnockbackListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class KnockbackAPI extends JavaPlugin {
    @Override
    public void onEnable() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new EntityKnockbackListener(), this);
        String masterPlugin = KnockbackAPIConfiguration.INSTANCE.getMasterPlugin();
        if (pluginManager.getPlugin(masterPlugin) == null) {
            getLogger().warning("The configured master-plugin \"" + masterPlugin + "\" wasn't found.");
        }
    }
}
