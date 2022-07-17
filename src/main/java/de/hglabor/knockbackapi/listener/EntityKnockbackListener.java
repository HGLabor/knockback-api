package de.hglabor.knockbackapi.listener;

import com.destroystokyo.paper.event.entity.EntityKnockbackByEntityEvent;
import de.hglabor.knockbackapi.api.KnockbackConfiguration;
import de.hglabor.knockbackapi.api.KnockbackSettings;
import de.hglabor.knockbackapi.config.KnockbackAPIConfiguration;
import de.hglabor.knockbackapi.registry.EntityKnockbackRegistry;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class EntityKnockbackListener implements Listener {
    @EventHandler
    public void knockback(EntityKnockbackByEntityEvent event) {
        Map<Plugin, KnockbackConfiguration> registryContents = EntityKnockbackRegistry.INSTANCE.getContents();
        AtomicReference<KnockbackConfiguration> knockbackConfiguration = new AtomicReference<>();
        if (registryContents.size() > 1) {
            Bukkit.getLogger().warning("Multiple plugins registered a knockback configuration, trying to use the configured master-plugin");
            registryContents.keySet().stream().filter(it -> it.getName().equalsIgnoreCase(KnockbackAPIConfiguration.INSTANCE.getMasterPlugin())).findFirst().ifPresent(masterPlugin -> {
                knockbackConfiguration.set(EntityKnockbackRegistry.INSTANCE.get(masterPlugin));
            });
        } else if(registryContents.size() > 0) {
            knockbackConfiguration.set(registryContents.get(registryContents.keySet().stream().findFirst().get()));
        } else {
            Bukkit.getLogger().warning("No plugin registered a knockback configuration");
        }
        KnockbackSettings knockbackSettings = knockbackConfiguration.get().getPlayerSettingsOrGlobal(event.getEntity().getUniqueId());
        if (!knockbackSettings.modifyKnockback) {
            return;
        }
        event.setCancelled(true);
        Entity hit = event.getEntity();
        Entity hitBy = event.getHitBy();
        double x = hitBy.getLocation().getX() - hit.getLocation().getX();
        double z = hitBy.getLocation().getZ() - hit.getLocation().getZ();
        double magnitude = Math.sqrt(x * x + z * z);
        Vector currentMovement = hit.getVelocity();
        Vector vec3d = new Vector(
                currentMovement.getX() + event.getAcceleration().getX(),
                currentMovement.getY() + event.getAcceleration().getY(),
                currentMovement.getZ() + event.getAcceleration().getZ()
        );
        hit.setVelocity(new Vector(
                (vec3d.getX() / knockbackSettings.knockbackFriction) - x / magnitude * knockbackSettings.knockbackHorizontal,
                Math.min(knockbackSettings.knockbackVerticalLimit, (vec3d.getY() / knockbackSettings.knockbackFriction) + knockbackSettings.knockbackVertical),
                (vec3d.getZ() / knockbackSettings.knockbackFriction) - z / magnitude * knockbackSettings.knockbackHorizontal
        ));
    }
}
