package com.example.flickrr

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ProgressBar
import com.example.flickrr.R.id.progress

class SplashScreen : AppCompatActivity() {
    private lateinit var progress:ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        progress=findViewById<ProgressBar>(R.id.progress)
        progress.visibility= View.VISIBLE
        Handler().postDelayed({
            progress.visibility=View.GONE
            val Intent = Intent(this,MainActivity::class.java)
            startActivity(Intent)
            finishAfterTransition()
        },3000)


//



    }
}