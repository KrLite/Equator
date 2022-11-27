package net.krlite.equator.base.sprite;

import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class HorizontalSprite {
    private final Identifier identifier;
    private final int step;

    public HorizontalSprite(Identifier identifier, int step) {
        this.identifier = identifier;
        this.step = step;
    }

    public HorizontalSprite(Identifier identifier, int textureWidth, int textureHeight) {
        this.identifier = identifier;
        step = textureWidth / textureHeight;
    }

    public Identifier getIdentifier() {
        return this.identifier;
    }

    public IdentifierSprite get(int index) {
        float
                uBegin = (float) (MathHelper.clamp(index, 1, this.step)  - 1) / this.step,
                uEnd = (float) MathHelper.clamp(index, 1, this.step) / this.step;

        return new IdentifierSprite(
                this.identifier,
                uBegin, 0, uEnd, 1
        );
    }
}
