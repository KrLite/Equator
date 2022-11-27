package net.krlite.equator.base.sprite;

import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class VerticalSprite {
    private final Identifier identifier;
    private final int step;

    public VerticalSprite(Identifier identifier, int step) {
        this.identifier = identifier;
        this.step = step;
    }

    public VerticalSprite(Identifier identifier, int textureWidth, int textureHeight) {
        this.identifier = identifier;
        step = textureHeight / textureWidth;
    }

    public Identifier getIdentifier() {
        return this.identifier;
    }

    public IdentifierSprite get(int index) {
        float
                vBegin = (float) (MathHelper.clamp(index, 1, this.step) - 1) / this.step,
                vEnd = (float) MathHelper.clamp(index, 1, this.step) / this.step;

        return new IdentifierSprite(
                this.identifier,
                0, vBegin, 1, vEnd
        );
    }
}
