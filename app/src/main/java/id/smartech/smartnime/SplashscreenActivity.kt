package id.smartech.smartnime

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import id.smartech.smartnime.base.BaseActivity
import id.smartech.smartnime.databinding.ActivitySplashscreenBinding

class SplashscreenActivity : BaseActivity<ActivitySplashscreenBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_splashscreen
        super.onCreate(savedInstanceState)

        startSplashscreen()
    }

    private fun startSplashscreen() {
        Handler().postDelayed({
            intents<MainActivity>(this)
            this.finish()
        }, 5000)
    }
}