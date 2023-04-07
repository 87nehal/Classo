package com.suvooh.Classo


import java.net.URLEncoder
import java.net.URL
import java.io.BufferedReader
import java.io.InputStreamReader

public class Translate {
    public fun translate(src:String,target: String,data:String):String {
        val sourceLang = src // replace with source language
        val targetLang = target // replace with target language

        val url = URL(
            "https://translate.google.com/translate_a/single?client=gtx&sl=${sourceLang}&tl=${targetLang}&dt=t&q=${
                URLEncoder.encode(
                    data,
                    "UTF-8"
                )
            }"
        )
        val connection = url.openConnection()
        connection.setRequestProperty("User-Agent", "Mozilla/5.0")

        val inputStream = connection.getInputStream()
        val reader = BufferedReader(InputStreamReader(inputStream, "UTF-8"))

        var response = ""
        var line = reader.readLine()
        while (line != null) {
            response += line
            line = reader.readLine()
        }

        inputStream.close()
        reader.close()

        return (response.split("\"")[1])
    }
}
