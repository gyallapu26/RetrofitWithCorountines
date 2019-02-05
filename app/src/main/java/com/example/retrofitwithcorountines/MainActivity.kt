package com.example.retrofitwithcorountines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var count  = 0


        button.setOnClickListener {
            count += 1
            Log.d("sos", "button is clicked .....${count}")
        }

        GlobalScope.launch(Dispatchers.Main) {
           try {

               //delay(5000)
               Log.d("sos", "Thread is ${Thread.currentThread().name}")
               textView.text =  APISerivce.getInstance().getCurrentWheather("London").await().toString()
               Log.d("sos", "after await ....")
               //  Log.d("sos", "current wheather is "+ APISerivce.getInstance().getCurrentWheather("London").await())
           }catch (e : Exception){
               Log.d("sos", " exception  ....${e.localizedMessage}")
           }
        }
    }
}
