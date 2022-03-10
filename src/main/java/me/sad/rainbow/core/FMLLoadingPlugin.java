package me.sad.rainbow.core;

import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.Mixins;

import java.util.Map;

@IFMLLoadingPlugin.MCVersion(ForgeVersion.mcVersion)
public class FMLLoadingPlugin implements IFMLLoadingPlugin {

    public FMLLoadingPlugin() {
        System.out.println("Injecting mixin");
        System.setProperty("devauth.enabled", "true");
        System.setProperty("devauth.account", "main");
        MixinBootstrap.init();
        Mixins.addConfiguration("rainbow.mixins.json");
    }

    @Override public String[] getASMTransformerClass() {
        return new String[0];
    }
    @Override public String getModContainerClass() {
        return null;
    }
    @Override public String getSetupClass() {
        return null;
    }
    @Override public void injectData(Map<String, Object> data) { }
    @Override public String getAccessTransformerClass() {
        return null;
    }
}