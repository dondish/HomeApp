package com.dondish.homeapp

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.ConsumerIrManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.Button
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private var irController: ConsumerIrManager? = null

    private val map = mapOf(
        Pair(R.id.avon, AV_ON),
        Pair(R.id.avstdby, AV_STDBY),
        Pair(R.id.monpower, MONITOR),
        Pair(R.id.scene1, XBOX),
        Pair(R.id.scene2, PS),
        Pair(R.id.scene3, STEAM),
        Pair(R.id.volup, VOL_UP),
        Pair(R.id.voldown, VOL_DOWN)
    )

    fun sendMessage(nec: IRMessage) {
        irController?.transmit(nec.frequency, nec.message)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        irController = getSystemService(Context.CONSUMER_IR_SERVICE) as ConsumerIrManager

        for ((id, code) in map) {
            val v = findViewById<Button>(id)

            v.setOnTouchListener { view, event ->
                when(event.action) {
                    MotionEvent.ACTION_DOWN-> {
                        sendMessage(code)
                        view.performClick()
                        view.isPressed = true
                        CoroutineScope(Dispatchers.IO).launch {
                                delay(40)
                                runBlocking {
                                    while (view.isPressed) {
                                        sendMessage(REPEAT)
                                        delay(97)
                                    }
                                }
                        }
                        true
                    }
                    MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> {
                        view.isPressed = false
                        true
                    }
                    else -> false
                }
            }
        }
    }
}
