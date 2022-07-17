package de.hglabor.knockbackapi.api;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class KnockbackConfiguration {
    public static final KnockbackConfiguration DEFAULT = new KnockbackConfiguration();

    public Map<UUID, KnockbackSettings> entitySettings;
    public KnockbackSettings globalConfiguration;

    public KnockbackSettings getPlayerSettingsOrGlobal(UUID entity) {
        return entitySettings.getOrDefault(entity, globalConfiguration);
    }

    public KnockbackConfiguration() {
        this(new HashMap<>(), KnockbackSettings.VANILLA_DEFAULTS);
    }

    public KnockbackConfiguration(Map<UUID, KnockbackSettings> entitySettings, KnockbackSettings globalConfiguration) {
        this.entitySettings = entitySettings;
        this.globalConfiguration = globalConfiguration;
    }
}
