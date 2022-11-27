package net.krlite.equator.base.sprite;

import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class SurfaceSprite {
    private final Identifier identifier;
    private final int stepX, stepY;

    public SurfaceSprite(Identifier identifier, int stepX, int stepY) {
        this.identifier = identifier;
        this.stepX = stepX;
        this.stepY = stepY;
    }

    public SurfaceSprite(Identifier identifier, int step) {
        this.identifier = identifier;
        stepX = step;
        stepY = step;
    }

    public Identifier getIdentifier() {
        return this.identifier;
    }

    public IdentifierSprite get(int index) {
        return get(index, index);
    }

    public IdentifierSprite get(int indexX, int indexY) {
        float
                uBegin = (float) (MathHelper.clamp(indexX, 1, stepX) - 1) / this.stepX,
                uEnd = (float) MathHelper.clamp(indexX, 1, stepX) / this.stepX,
                vBegin = (float) (MathHelper.clamp(indexY, 1, stepY) - 1) / this.stepY,
                vEnd = (float) MathHelper.clamp(indexY, 1, stepY) / this.stepY;

        return new IdentifierSprite(
                identifier,
                uBegin, vBegin, uEnd, vEnd
        );
    }
}
