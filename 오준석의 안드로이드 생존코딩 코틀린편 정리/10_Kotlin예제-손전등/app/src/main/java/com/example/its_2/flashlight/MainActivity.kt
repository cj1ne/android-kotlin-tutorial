package com.example.its_2.flashlight

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.intentFor

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        flashSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                startService(intentFor<TorchService>().setAction("on"))
            } else {
                startService(intentFor<TorchService>().setAction("off"))
            }
        }


        /* 액티비티에서 손전등 끄고 키기

        var torch = Torch(this)

        flashSwitch.setOnCheckedChangeListener{ buttonView, isChecked ->
            if(isChecked) {
                torch.flashOn()
            } else {
                torch.flashOff()
            }
        }
        */
    }
}
