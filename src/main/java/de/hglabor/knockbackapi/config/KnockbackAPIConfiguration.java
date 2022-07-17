package de.hglabor.knockbackapi.config;

import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;
import java.nio.file.Files;
import java.nio.file.Path;

public class KnockbackAPIConfiguration {
    public static final KnockbackAPIConfiguration INSTANCE = new KnockbackAPIConfiguration();

    private final Path path = Path.of("config", "knockback-api.yaml");
    private final ConfigurationNode root;

    public KnockbackAPIConfiguration() {
        try {
            if (!Files.exists(path)) {
                Files.createFile(path);
            }
            final YamlConfigurationLoader loader = YamlConfigurationLoader.builder().path(path).build();
            root = loader.load();
            ConfigurationNode masterNode = root.node("master-plugin");
            String masterPlugin = masterNode.getString();
            if (masterPlugin == null) {
                masterNode.set("just-another-plugin");
                loader.save(root);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getMasterPlugin() {
        return root.node("master-plugin").getString();
    }
}
