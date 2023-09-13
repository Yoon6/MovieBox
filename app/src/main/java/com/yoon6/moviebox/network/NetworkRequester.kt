package com.yoon6.moviebox.network

import com.yoon6.moviebox.BuildConfig
import org.json.JSONObject
import java.io.BufferedInputStream
import java.net.HttpURLConnection
import java.net.URL

class NetworkRequester {

    fun get(url: String): String {
        val connection = URL(url).openConnection() as HttpURLConnection
        with(connection) {
            setRequestProperty("accept", "application/json")
            setRequestProperty("Authorization", "Bearer ${BuildConfig.TMDB_API_ACCESS_TOKEN}")
        }

        if (connection.responseCode == HttpURLConnection.HTTP_OK) {
            val br = BufferedInputStream(connection.inputStream).bufferedReader()

            val responseBody = StringBuilder()
            while (true) {
                val data = br.readLine() ?: break
                responseBody.append(data)
            }

            br.close()
            connection.disconnect()

            val jsonObject = JSONObject(responseBody.toString())

            return responseBody.toString()
        }
        return ""
    }
}