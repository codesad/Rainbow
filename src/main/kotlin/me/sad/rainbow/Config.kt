package me.sad.rainbow

import gg.essential.vigilance.Vigilant
import gg.essential.vigilance.data.Property
import gg.essential.vigilance.data.PropertyType
import java.awt.Color
import java.io.File

object Config : Vigilant(File("config/Rainbow.toml"), "Rainbow") {
    @JvmStatic
    @Property(type = PropertyType.SWITCH, name = "&3Highlight Hex", description = "Highlights text that contains hex codes.\n&7Example: &c#FF5555&7.", category = "Text")
    var hexHighlight = true

    @JvmStatic
    @Property(type = PropertyType.SWITCH, name = "&6Disable Sign Lighting", description = "Disables the lighting on signs.\nMakes colours more accurate.", category = "Text")
    var disableSignLighting = true

    @JvmStatic
    @Property(type = PropertyType.SWITCH, name = "&aHighlight Exotics", description = "Highlights exotic pieces.", category = "Items")
    var exoticHighlight = true

    @JvmStatic
    @Property(type = PropertyType.COLOR, name = "&cExotic Colour", description = "Highlight colour for exotic pieces.\nDoes not include crystal & fairy.", category = "Items")
    var exoticColour = Color(30, 210, 170, 120)

    @JvmStatic
    @Property(type = PropertyType.SWITCH, name = "&aHighlight Crystal", description = "Highlights crystal pieces.", category = "Items")
    var crystalHighlight = true

    @JvmStatic
    @Property(type = PropertyType.COLOR, name = "&5Crystal Colour", description = "Highlight colour for crystal pieces.", category = "Items")
    var crystalColour = Color(130, 16, 160, 120)

    @JvmStatic
    @Property(type = PropertyType.SWITCH, name = "&aHighlight Fairy", description = "Highlights fairy pieces.", category = "Items")
    var fairyHighlight = true


    @JvmStatic
    @Property(type = PropertyType.COLOR, name = "&dFairy Colour", description = "Highlight colour for fairy pieces.", category = "Items")
    var fairyColour = Color(250, 30, 160, 120)

    @JvmStatic
    @Property(type = PropertyType.SWITCH, name = "&2Highlight Glitched", description = "Highlights pieces acquired through bugs.", category = "Items")
    var glitchedHighlight = true


    @JvmStatic
    @Property(type = PropertyType.COLOR, name = "&2Glitched Colour", description = "Highlight colour for glitched pieces.", category = "Items")
    var glitchedColour = Color(126, 213, 80, 120)

    @JvmStatic
    @Property(type = PropertyType.SWITCH, name = "&bDisable Enchant Glint", description = "Disables the enchant glint ONLY for leather armour pieces.", category = "Items")
    var disableEnchantGlint = false

    @JvmStatic
    @Property(type = PropertyType.SWITCH, name = "&aAlways Show Hex", description = "Displays the hex of an armour piece even when advanced tooltips are off.", category = "Items")
    var alwaysShowHex = true

    init {
        addDependency("exoticColour", "exoticHighlight")
        addDependency("crystalColour", "crystalHighlight")
        addDependency("fairyColour", "fairyHighlight")
    }
}