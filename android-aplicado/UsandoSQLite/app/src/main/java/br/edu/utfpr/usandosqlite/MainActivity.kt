package br.edu.utfpr.usandosqlite

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        println("onCreate executado!")
    }

    override fun onStart() {
        super.onStart()
        println("onStart executado!")
    }

    override fun onResume() {
        super.onResume()
        println("onResume executado!")
    }

    override fun onPause() {
        super.onPause()
        println("onPause executado!")
    }

    override fun onStop() {
        super.onStop()
        println("onStop executado!")
    }

    override fun onDestroy() {
        super.onDestroy()
        println("onDestroy executado!")
    }

    override fun onRestart() {
        super.onRestart()
        println("onRestart executado!")
    }
}
