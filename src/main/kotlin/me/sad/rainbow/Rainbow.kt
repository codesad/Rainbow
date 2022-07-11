package me.sad.rainbow

import me.sad.rainbow.events.TooltipEvent
import net.minecraft.client.Minecraft
import net.minecraft.event.ClickEvent
import net.minecraft.event.HoverEvent
import net.minecraft.util.ChatComponentText
import net.minecraft.util.ChatStyle
import net.minecraftforge.client.ClientCommandHandler
import net.minecraftforge.common.ForgeVersion
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.entity.player.PlayerSetSpawnEvent
import net.minecraftforge.fml.common.Loader
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import org.apache.logging.log4j.LogManager


@SideOnly(Side.CLIENT)
@Mod(modid = Rainbow.MOD_ID, name = Rainbow.MOD_NAME, version = "1.1.0", updateJSON = Rainbow.UPDATE_URL)
class Rainbow {
    @Mod.EventHandler
    fun onInit(event: FMLInitializationEvent) {
        LOGGER.info("Initialising Rainbow.")
        MinecraftForge.EVENT_BUS.register(this)
        MinecraftForge.EVENT_BUS.register(TooltipEvent())
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

    @SubscribeEvent
    fun onJoinWorld(event: PlayerSetSpawnEvent?) {
        if (checkedForUpdate) return
        val result = ForgeVersion.getResult(Loader.instance().activeModContainer())
        println(result.status)
        val betaOutdated = result.status == ForgeVersion.Status.BETA_OUTDATED
        if (result.status == ForgeVersion.Status.OUTDATED || betaOutdated) {
            val player = Minecraft.getMinecraft().thePlayer
            val newSplit = result.target.toString().split(".")
            val curSplit = Loader.instance().activeModContainer().version.split(".")
            var type = ""
            for (i in newSplit.size - 1 downTo 0) {
                if (curSplit[i] != newSplit[i]) {
                    type = when (i) {
                        0 -> "\u00a74Major"
                        1 -> "\u00a76Minor"
                        2 -> "\u00a7ePatch"
                        else -> ""
                    }
                }
            }
            player.addChatMessage(
                ChatComponentText(
                    "$PREFIX A new ${type}\u00a7r update is available!"
                )
            )
            player.addChatMessage(ChatComponentText("$PREFIX Click\u00a7b [HERE]\u00a7f to download it.").setChatStyle(ChatStyle()
                .setChatClickEvent(ClickEvent(ClickEvent.Action.OPEN_URL, result.url))
                .setChatHoverEvent(HoverEvent(HoverEvent.Action.SHOW_TEXT, ChatComponentText("\u00a7eUpdate to \u00a7b${result.target}\u00a7e!"))
            )))
        }
        checkedForUpdate = true
    }
}