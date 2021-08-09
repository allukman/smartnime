package id.smartech.smartnime.ui.splash

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatDelegate
import id.smartech.smartnime.R
import id.smartech.smartnime.base.BaseActivity
import id.smartech.smartnime.databinding.ActivitySplashscreenBinding
import id.smartech.smartnime.ui.main.MainActivity

class SplashscreenActivity : BaseActivity<ActivitySplashscreenBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_splashscreen
        super.onCreate(savedInstanceState)

        startSplashscreen()

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }

    private fun startSplashscreen() {
        Handler().postDelayed({
            intents<MainActivity>(this)
            this.finish()
        }, 5000)
    }
}