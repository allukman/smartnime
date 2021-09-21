package id.smartech.smartnime.ui.nav.information

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import id.smartech.smartnime.R
import id.smartech.smartnime.base.BaseActivity
import id.smartech.smartnime.databinding.ActivityInformationBinding
import id.smartech.smartnime.ui.sidebar.SidebarActivity

class InformationActivity : BaseActivity<ActivityInformationBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_information
        super.onCreate(savedInstanceState)

        setOnClick()
    }

    private fun setOnClick() {
        bind.back.setOnClickListener {
            intents<SidebarActivity>(this)
        }

        bind.lukman.setOnClickListener {
            intentBrowser("https://www.instagram.com/allukman__/")
        }

        bind.jeff.setOnClickListener {
            intentBrowser("https://www.instagram.com/jeff_aria_/")
        }

        bind.syahrul.setOnClickListener {
            intentBrowser("https://www.instagram.com/astronotdarat___/")
        }
    }

    private fun intentBrowser(url: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    }
}