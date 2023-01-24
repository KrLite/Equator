### <p align=right>[`→` Modrinth](https://modrinth.com/mod/equator)</p>

![Banner](artwork/banner.png)

### A Simple Rendering Library for Minecraft.

**Equator** is a lightweight library designed to make rendering in Minecraft easier.

With **Equator,** you can render and manage texture resources with ease, and get access to many useful rendering utilities.

## TL;DR

```groovy
repositories {
    maven { url = "https://api.modrinth.com/maven" }
    maven { url "https://jitpack.io" }
}

dependencies {
    modImplementation "maven.modrinth:equator:{x}"
    implementation include("com.github.KrLite:Equator-Utils:v{x}")
}
```

## Features

- **Equator** - The main class for rendering, including **Renderer** for sprites, **Painter** for colors and shapes, and **ItemModel** and **BlockModel** to render models in ease.
- **Math** - Access easing functions and analyse angles, coordinates, etc.
- **Utilities** - Use **Pusher** and **Timer** to handle animations and rendering rules in a simpler way.
- **Management** - Manage your textures as **IdentifierSprites** and render them with **Rect**s and **TintedRect**s.
- **More** - Equator Lib is in active development! More features will be added soon.

## Implementation

You can use **[Modrinth Maven](https://docs.modrinth.com/docs/tutorials/maven/)** to implement **Equator** and **[JitPack](https://jitpack.io/#KrLite/Equator)** to implement **[Equator Utils.](https://github.com/KrLite/Equator-Utils)**

Add the content below to your `build.gradle` file:

```groovy
repositories {
    // Modrinth Maven, for the mod Equator
    maven { url = "https://api.modrinth.com/maven" }
    
    // JitPack, for Equator Utils(you probably want this too)
    maven { url 'https://jitpack.io' }
}

dependencies {
    modImplementation "maven.modrinth:equator:{x}"
    implementation include("com.github.KrLite:Equator-Utils:v{x}")
}
```

In above:

- `{x}` in `maven.modrinth:equator:{x}` should be the latest [`version id`](https://modrinth.com/mod/equator/versions) of **Equator.**
- `v{x}` in `com.github.KrLite:Equator-Utils:v{x}` should be the latest [`tag name`](https://github.com/KrLite/Equator-Utils/tags) of Equator Utils.

If you do not implement **Equator Utils,** **Equator** can still function fully, but you may not be able to access many convenient methods that **Equator** uses. To only implement **Equator,** refer to the content below:

```groovy
repositories {
    maven { url = "https://api.modrinth.com/maven" }
}

dependencies {
    modImplementation "maven.modrinth:equator:{x}"
}
```

## Dependency

Finally, don't forget to add **Equator** as a mod dependent in your `fabric.mod.json:`

```json
"depends": {
    "equator": "*"
}
```

And Quilt, in your `quilt.mod.json:`

```json
"depends": [
      {
        "id": "equator",
        "versions": "*"
      }
    ]
```
