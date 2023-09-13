package com.yoon6.moviebox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.yoon6.moviebox.network.NetworkRequester
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val contentState = MutableSharedFlow<String>()
    private lateinit var textView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView = findViewById(R.id.tv_content)

        CoroutineScope(Dispatchers.IO).launch {
            val networkRequester = NetworkRequester()
            val response = networkRequester.get("https://api.themoviedb.org/3/movie/popular?language=ko_KR&page=1")
            contentState.emit(response)
        }

        CoroutineScope(Dispatchers.Main).launch {
            contentState.collect {
                textView.text = it
            }
        }
    }
}