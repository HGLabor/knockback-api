package de.hglabor.knockbackapi.api;

import org.bukkit.Bukkit;

public class KnockbackSettings {
    public static final KnockbackSettings VANILLA_DEFAULTS = new KnockbackSettings();

    public boolean modifyKnockback;
    public double knockbackFriction;
    public double knockbackHorizontal;
    public double knockbackVerticalLimit;
    public double knockbackVertical;

    public KnockbackSettings() {
        this(false, 2.0D, 0.4D, 0.4D, 0.4D);
    }

    public KnockbackSettings(boolean modifyKnockback, double knockbackFriction, double knockbackHorizontal, double knockbackVerticalLimit, double knockbackVertical) {
        modifyKnockback(modifyKnockback);
        knockbackFriction(knockbackFriction);
        knockbackHorizontal(knockbackHorizontal);
        knockbackVerticalLimit(knockbackVerticalLimit);
        knockbackVertical(knockbackVertical);
    }

    public KnockbackSettings modifyKnockback(boolean bl) {
        modifyKnockback = bl;
        return this;
    }

    public KnockbackSettings knockbackFriction(double d) {
        knockbackFriction = d;
        return this;
    }

    public KnockbackSettings knockbackHorizontal(double d) {
        knockbackHorizontal = d;
        return this;
    }

    public KnockbackSettings knockbackVertical(double d) {
        knockbackVertical = d;
        if (d > knockbackVerticalLimit) {
            Bukkit.getLogger().warning("Vertical knockback is higher than the vertical knockback limit!");
        }
        return this;
    }

    public KnockbackSettings knockbackVerticalLimit(double d) {
        knockbackVerticalLimit = d;
        return this;
    }
}
