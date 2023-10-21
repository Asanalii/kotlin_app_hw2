package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.os.PersistableBundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.myapplication.databinding.ActivityMainBinding
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpSayHelloButton()
        setUpsendTextButton()
    }

    private fun setUpSayHelloButton(){
        binding.sayHelloBtn.setOnClickListener {
            if(isValid()){
                val intent = Intent(this, HelloActivity::class.java)
                intent.putExtra(ArgumentKey.NAME.name, binding.nameInputView.text.toString())
                startActivity(intent)
            } else Toast.makeText(this, "You need input your name", Toast.LENGTH_SHORT).show()
        }
    }


    private fun setUpsendTextButton(){
         binding.sendBtn.setOnClickListener {
             if (isValid()) {

                 val intent = Intent(Intent.ACTION_SEND)
                 intent.putExtra(Intent.EXTRA_TEXT, binding.nameInputView.text.toString())
                 intent.type = "text/plain"
                 val chooseIntent = Intent.createChooser(intent, "Title")
                 startActivity(chooseIntent)

             } else Toast.makeText(this, "You need input your name", Toast.LENGTH_SHORT).show()
         }
    }

    private fun isValid() = !binding.nameInputView.text.isNullOrBlank()



}

enum class ArgumentKey{
    NAME,
}