package me.sad.rainbow

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

object Colours {
    @JvmStatic
    val exotic = mutableMapOf<String, List<Int>>()
    @JvmStatic
    val glitched = mutableMapOf<String, List<Int>>()
    @JvmStatic
    val fairy = mutableListOf<Int>()
    @JvmStatic
    val crystal = mutableListOf<Int>()

    @JvmStatic
    val fairyIds = listOf(
        "FAIRY_BOOTS",
        "FAIRY_LEGGINGS",
        "FAIRY_CHESTPLATE",
        "FAIRY_HELMET"
    )

    @JvmStatic
    val crystalIds = listOf(
        "CRYSTAL_BOOTS",
        "CRYSTAL_LEGGINGS",
        "CRYSTAL_CHESTPLATE",
        "CRYSTAL_HELMET"
    )

    fun fetchColours() {
        val url = URL("https://gist.githubusercontent.com/codesad/16ecce757e53d507ab63de9c22f7d811/raw/")
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"
        connection.connect()
        val inputStream = connection.inputStream
        val reader = BufferedReader(InputStreamReader(inputStream))
        val stringBuilder = StringBuilder()
        var line: String? = reader.readLine()
        while (line != null) {
            stringBuilder.append(line)
            line = reader.readLine()
        }
        val json: JsonObject = JsonParser().parse(stringBuilder.toString()).asJsonObject
        json.get("exotics").asJsonObject.entrySet().forEach { (name, colour) ->
            exotic[name] = colour.asString.split("|").map { Integer.decode(it) }
        }
        json.get("glitched").asJsonObject.entrySet().forEach { (name, colour) ->
            glitched[name] = colour.asString.split("|").map { Integer.decode(it) }
        }
        json.get("fairy").asJsonArray.forEach { fairy.add(Integer.decode(it.asString)) }
        fairyIds.forEach { exotic[it] = fairy }
        json.get("crystal").asJsonArray.forEach { crystal.add(Integer.decode(it.asString)) }
        crystalIds.forEach { exotic[it] = crystal }
    }
}