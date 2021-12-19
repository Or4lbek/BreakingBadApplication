package com.sapar.breakingbadapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.sapar.breakingbadapplication.Registration.RegistrationActivity
import com.sapar.breakingbadapplication.Registration.Session

class SplashScreenActivity : AppCompatActivity() {
    private val splashScreenTime:Long = 2000
    lateinit var session:Session;
    var h = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        session = Session(this)

        h.postDelayed({
//            val intent: Intent = Intent(this,RegistrationActivity::class.java)
//            startActivity(intent)
//            finish()
//            println(session.loggenIn())
            if (session.loggenIn()) {
                val i = Intent(this, MainActivity::class.java)
                startActivity(i)
                finish()
            } else {
                val i = Intent(this, RegistrationActivity::class.java)
                startActivity(i)
                finish()
            }
        }, splashScreenTime)
    }
}