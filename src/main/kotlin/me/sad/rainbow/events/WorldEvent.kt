package me.sad.rainbow.events

import me.sad.rainbow.Rainbow
import net.minecraft.client.Minecraft
import net.minecraft.event.ClickEvent
import net.minecraft.event.HoverEvent
import net.minecraft.util.ChatComponentText
import net.minecraft.util.ChatStyle
import net.minecraftforge.common.ForgeVersion
import net.minecraftforge.event.entity.player.PlayerSetSpawnEvent
import net.minecraftforge.fml.common.Loader
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

class WorldEvent {
    @SubscribeEvent
    fun onJoinWorld(unused: PlayerSetSpawnEvent?) {
        if (Rainbow.checkedForUpdate) return
        val result = ForgeVersion.getResult(Loader.instance().activeModContainer())
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
                    "${Rainbow.PREFIX} A new ${type}\u00a7r update is available!"
                )
            )
            player.addChatMessage(
                ChatComponentText("${Rainbow.PREFIX} Click\u00a7b [HERE]\u00a7f to download it.").setChatStyle(
                    ChatStyle()
                        .setChatClickEvent(ClickEvent(ClickEvent.Action.OPEN_URL, result.url))
                        .setChatHoverEvent(
                            HoverEvent(
                                HoverEvent.Action.SHOW_TEXT,
                                ChatComponentText("\u00a7eUpdate to \u00a7b${result.target}\u00a7e!")
                            )
                        )
                )
            )
        }
        Rainbow.checkedForUpdate = true
    }
}