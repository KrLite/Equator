### <p align=right>[`→` Modrinth](https://modrinth.com/mod/equator)</p>

![Banner](artwork/banner.png)

### A Simple Rendering Library for Minecraft.

**Equator** is a lightweight library designed to make rendering in Minecraft easier.

With **Equator,** you can render and manage texture resources with ease, and get access to many useful rendering utilities.

## Features

- **Equator** - The main class for rendering, including **Renderer** for sprites, **Painter** for colors and shapes, and **ItemModel** and **BlockModel** to render models in ease.
- **Math** - Access easing functions and analyse angles, coordinates, etc.
- **Utilities** - Use **Pusher** and **Timer** to handle animations and rendering rules in a simpler way.
- **Management** - Manage your textures as **IdentifierSprites** and render them with **Rect**s and **TintedRect**s.
- **More** - Equator Lib is in active development! More features will be added soon.

## Implementation

You can use **[Modrinth Maven](https://docs.modrinth.com/docs/tutorials/maven/)** or **[JitPack](https://jitpack.io/#KrLite/Equator)** to implement Equator.

Add the content below to your `build.gradle` file:

```groovy
repositories {
    // If you are using Modrinth Maven
    maven { url = "https://api.modrinth.com/maven" }
    
    // If you are using JitPack
    maven { url 'https://jitpack.io' }
}

dependencies {
    // Modrinth Maven
    modImplementation "maven.modrinth:equator:xxx"
    
    // JitPack
    modImplementation "com.github.KrLite:Equator:va.b.c+xxx"
}
```

Remember to replace the `a.b.c` above with the target `Minecraft version`, and the `xxx` above with the latest [`version id`](https://modrinth.com/mod/equator/versions) of **Equator,** or the [`tag name`](https://github.com/KrLite/Equator/tags) if you are using JitPack.

And finally, don't forget to add Equator as a mod dependent in your `fabric.mod.json:`

```json
"depends": {
    "equator": "*"
}
```

Or if on Quilt, `quilt.mod.json:`

```json
"depends": [
      {
        "id": "equator",
        "versions": "*"
      }
    ]
```
