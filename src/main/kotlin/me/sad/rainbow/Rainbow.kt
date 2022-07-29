package me.sad.rainbow

import me.sad.rainbow.events.WorldEvent
import me.sad.rainbow.features.LoreHex
import net.minecraftforge.client.ClientCommandHandler
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import org.apache.logging.log4j.LogManager


@SideOnly(Side.CLIENT)
@Mod(modid = Rainbow.MOD_ID, name = Rainbow.MOD_NAME, version = "1.1.1", updateJSON = Rainbow.UPDATE_URL)
class Rainbow {
    @Mod.EventHandler
    fun onInit(unused: FMLInitializationEvent) {
        LOGGER.info("Initialising Rainbow.")
        MinecraftForge.EVENT_BUS.register(LoreHex())
        MinecraftForge.EVENT_BUS.register(WorldEvent())
        ClientCommandHandler.instance.registerCommand(Command())
        Config.initialize()
        Config.markDirty()
        Colours.fetchColours()
    }

    companion object {
        var checkedForUpdate = false
        const val MOD_ID = "rainbow"
        const val MOD_NAME = "Rainbow"
        const val UPDATE_URL = "https://gist.github.com/codesad/d2889f39e6d2a77265d7b13420472099/raw/"
        const val PREFIX = "\u00a7cR\u00a76a\u00a7ei\u00a7an\u00a7bb\u00a79o\u00a7dw \u00a75● \u00a7f"
        @get:JvmName("getLogger")
        @JvmStatic
        val LOGGER = LogManager.getLogger(MOD_ID)!!
    }
}