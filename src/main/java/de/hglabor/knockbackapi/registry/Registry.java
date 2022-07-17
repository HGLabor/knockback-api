package de.hglabor.knockbackapi.registry;

import org.bukkit.plugin.Plugin;

import java.util.Map;

/**
 * A registry contains stored objects bound to a owning plugin.
 * @param <T> Type of objects that are stored in this registry
 */
public interface Registry<T> {

    Map<Plugin, T> getContents();

    T get(Plugin owningPlugin);

    default void register(Plugin owningPlugin, T object) {
        getContents().put(owningPlugin, object);
    }

}
