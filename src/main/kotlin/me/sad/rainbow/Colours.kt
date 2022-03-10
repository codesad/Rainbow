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
    val fairy = mutableListOf<Int>()
    @JvmStatic
    val crystal = mutableListOf<Int>()

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
        for (i in json.get("exotics").asJsonObject.entrySet()) {
            val name = i.key
            val colour = i.value.asString
            exotic[name] = colour.toString().split("|").map { Integer.decode(it) }
        }
        for (i in json.get("fairy").asJsonArray) {
            fairy.add(Integer.decode(i.asString))
        }
        for (i in json.get("crystal").asJsonArray) {
            crystal.add(Integer.decode(i.asString))
        }
    }
}