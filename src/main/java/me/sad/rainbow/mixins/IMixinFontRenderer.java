package me.sad.rainbow.mixins;

import net.minecraft.client.gui.FontRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(FontRenderer.class)
public interface IMixinFontRenderer {
    @Accessor("red")
    float red();

    @Accessor("green")
    float green();

    @Accessor("blue")
    float blue();

    @Accessor("alpha")
    float alpha();

    @Accessor("colorCode")
    int[] colorCode();
}
