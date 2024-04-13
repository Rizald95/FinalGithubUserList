package com.example.finalgithubuserlist.ui.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageView
import com.example.finalgithubuserlist.R
import com.example.finalgithubuserlist.databinding.ActivitySplashScreenBinding
import com.example.finalgithubuserlist.ui.main.MainActivity

@Suppress("DEPRECATION")
class SplashScreen : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    companion object {
        private const val SPLASH_SCREEN_ASTRONAUT_DURATION: Long = 3000
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        supportActionBar?.hide()

        val logoImageView: ImageView = findViewById(R.id.logoImageView)
        logoImageView.setImageResource(R.drawable.astrologo)

        Handler().postDelayed({
            val intent = Intent(this@SplashScreen, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_SCREEN_ASTRONAUT_DURATION)

        val astronaut: ImageView = findViewById(R.id.logoImageView)

        astronaut.apply {
            alpha = 0f
            visibility = View.VISIBLE
            animate().alpha(1f).setDuration(1500).setListener(null)
        }
    }
}