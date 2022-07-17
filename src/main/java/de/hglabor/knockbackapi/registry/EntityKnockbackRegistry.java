package de.hglabor.knockbackapi.registry;

import de.hglabor.knockbackapi.api.KnockbackConfiguration;
import org.bukkit.plugin.Plugin;
import java.util.HashMap;
import java.util.Map;

public class EntityKnockbackRegistry implements Registry<KnockbackConfiguration> {
    public static final EntityKnockbackRegistry INSTANCE = new EntityKnockbackRegistry();

    private final Map<Plugin, KnockbackConfiguration> map = new HashMap<>();

    @Override
    public Map<Plugin, KnockbackConfiguration> getContents() {
        return map;
    }

    @Override
    public KnockbackConfiguration get(Plugin owningPlugin) {
        return getContents().getOrDefault(owningPlugin, KnockbackConfiguration.DEFAULT);
    }
}
