### <p align=right>[Modrinth →](https://modrinth.com/mod/equator)</p>

![Banner](artwork/banner.png)

### A Simple Rendering Library for Minecraft.

Equator Lib is a lightweight library designed to make rendering in Minecraft easier.

With Equator Lib, you can render and manage texture resources with ease, and 
also get access to some useful rendering utilities.

## Features

- **Equator** - A simple rendering class that allows you to render quads and 
  textured quads with ease.
- **Math** - Use easing functions and solve annoying angles and coordinates in a second.
- **Utilities** - Get access to some useful classes such as **IdentifierBuilder** to easily create identifiers for your textures and translation keys.
- **Management** - Manage your textures as **IdentifierSprites** and render them with **MatrixWrapper** to avoid the hassle of matrix coordinate transformations.
- **More** - Equator Lib is in active development! More features will be added soon.

## Implementation

Equator Lib is using the **[Modrinth Maven.](https://docs.modrinth.com/docs/tutorials/maven/#loom-fabric-quilt-architectury)**

Add the content below to your `build.gradle` file:

```groovy
repositories {
    exclusiveContent {
        forRepository {
            maven {
                name = "Modrinth"
                url = "https://api.modrinth.com/maven"
            }
        }
        filter {
            includeGroup "maven.modrinth"
        }
    }
}
```

```groovy
dependencies {
    modAImplementation "maven.modrinth:equator:xxx"
}
```

Remember to replace `xxx` above with the latest [`version id`](https://modrinth.com/mod/equator/versions) of Equator Lib.

> It's recommended to use the `version id` instead of the `version number.` For example, using `equator:nFvoDWks` is better than using `equator:1.19-1.0.0-beta.`
