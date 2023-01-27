### <p align=right>[`→` Modrinth](https://modrinth.com/mod/equator)</p>

![Banner](artwork/banner.png)

### A Simple Rendering Library for Minecraft.

**Equator** is a lightweight library designed to make rendering in Minecraft easier.

With **Equator,** you can render and manage texture resources with ease, and get access to many useful rendering utilities.

## TL;DR

### 1. Implement Equator in your `build.gradle`

```groovy
repositories {
    maven { url "https://jitpack.io" }
}

dependencies {
    modImplementation "com.github.KrLite:Equator:{a.b.c}+{v1}"
    implementation include("com.github.KrLite:Equator-Utils:{v2}")
}
```

> In above:
>
> - `{a.b.c}` The target `Minecraft version`
> - `{v1}` The latest [`JitPack version`](https://jitpack.io/#KrLite/Equator) of **Equator**
> - `v{x2}` The latest [`JitPack version`](https://jitpack.io/#KrLite/Equator-Utils) of **Equator Utils**

### 2. Add Equator as a mod dependent

```json
"depends": {
  "equator": "*"
}
```

## Features

- **Equator** - The main class for rendering, including **Renderer** for sprites, **Painter** for colors and shapes, and **ItemModel** and **BlockModel** to render models in ease.
- **Math** - Access easing functions and analyse angles, coordinates, etc.
- **Utilities** - Use **Pusher** and **Timer** to handle animations and rendering rules in a simpler way.
- **Management** - Manage your textures as **IdentifierSprites** and render them with **Rect**s and **TintedRect**s.
- **More** - Equator Lib is in active development! More features will be added soon.

## Implementation

You can use **[Modrinth Maven](https://docs.modrinth.com/docs/tutorials/maven/)** to implement **Equator,** or use **[JitPack](https://jitpack.io/#KrLite/Equator)** to implement both **Equator** and **[Equator Utils.](https://github.com/KrLite/Equator-Utils)**

Add the content below to your `build.gradle:`

- **Modrinth Maven**

```groovy
repositories {
    maven { url = "https://api.modrinth.com/maven" }
    maven { url 'https://jitpack.io' }
}

dependencies {
    modImplementation "maven.modrinth:equator:{v1}"
    implementation include("com.github.KrLite:Equator-Utils:{v2}")
}
```

> In above:
>
> - `{v1}` The latest [`Modrinth version id`](https://modrinth.com/mod/equator/versions) of **Equator**
> - `{v2}` The latest [`JitPack version`](https://jitpack.io/#KrLite/Equator-Utils) of **Equator Utils**

> For example: `{v1} → v1.0` `{v2} → v1.0.0`

- **JitPack**

```groovy
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    modImplementation "com.github.KrLite:Equator:{a.b.c}+{v1}"
    implementation include("com.github.KrLite:Equator-Utils:{v2}")
}
```

> In above:
> 
> - `{a.b.c}` The target `Minecraft version`
> - `{v1}` The latest [`JitPack version`](https://jitpack.io/#KrLite/Equator) of **Equator**
> - `v{x2}` The latest [`JitPack version`](https://jitpack.io/#KrLite/Equator-Utils) of **Equator Utils**

> For example: `{a.b.c}+{v1} → 1.19.3+v1.0` `{v2} → v1.0.0`

If you do not implement **Equator Utils,** **Equator** can still function fully, but you may not be able to access many convenient methods that **Equator** uses.

## Dependency

Finally, don't forget to add **Equator** as a mod dependent in your `fabric/quilt.mod.json:`

- **Fabric**

```json
"depends": {
  "equator": "*"
}
```

- **Quilt**

```json
"depends": [
  {
    "id": "equator",
    "versions": "*"
  }
]
```
