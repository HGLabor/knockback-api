# knockback-api

KnockbackAPI is really a simple and easy to use api to configure the knockback behaivor of entities.
It allows you to turn the minecraft knockback completely upside-down!

## Tutorial

**java docs soon™**

### Add the dependency

KnockbackAPI is available on the centarl maven repository.

**Kotlin Gradle DSL**

```kotlin
dependencies {
    implementation("de.hglabor:knockback-api:1.19.0")
}
```

**Groovy Gradle**

```groovy
dependencies {
    implementation 'de.hglabor:knockback-api:1.19.0'
}
```

**Maven**

```xml
<dependency>
    <groupId>de.hglabor</groupId>
    <artifactId>knockback-api</artifactId>
    <version>1.19.0</version>
</dependency>
```

### Let's knock entities to the aether!

In this tutorial we will turn up the vertical knockback until they fly to the aether.

If you have multiple plugins registering a knockback configuration, enter the name of the plugin whose config you want to use in the configuration file (`config/knockback-api.yaml`)

Start by creating a normal paper plugin and registering a new knockback configuration:

```java
public class Example extends JavaPlugin {
    @Override
    public void onEnable() {
        EntityKnockbackRegistry.INSTANCE.register(
                this,
                new KnockbackConfiguration(
                        Map.of(),
                        new KnockbackSettings()
                                .modifyKnockback(true)
                                .knockbackVerticalLimit(6)
                                .knockbackVertical(6)
                ));
    }
}
```

⚠️ **Please note:** `knockbackVerticalLimit` must always be higher or equal than `knockbackVertical`

### I don't like a player, let's knock them to the other side of the world!

If you want to create a per-player configuration, just place it in the map

```java
public class Example extends JavaPlugin {
    @Override
    public void onEnable() {
        EntityKnockbackRegistry.INSTANCE.register(
                this,
                new KnockbackConfiguration(
                        Map.of(
                                UUID.fromString("50bf6931-e149-4743-9210-92cd58d85c5d"),
                                new KnockbackSettings()
                                        .modifyKnockback(true)
                                        .knockbackHorizontal(6.0)
                        ),
                        new KnockbackSettings()
                                .modifyKnockback(true)
                                .knockbackVerticalLimit(6.0)
                                .knockbackVertical(6.0)
                ));
    }
}
```

### Jokes aside

`6.0` is a very high number for knockback you will have to find good values by yourself.
If you want to replicate the 1.8 knockback, [taito2019](https://github.com/taito2019) made a good set of values, these aren't the original ones but i bet you won't see a real difference!

```java
new KnockbackSettings()
        .modifyKnockback(true)
        .knockbackFriction(3.0)
        .knockbackHorizontal(0.64)
```