package id.smartech.smartnime.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.smartech.smartnime.R
import id.smartech.smartnime.base.BaseActivity
import id.smartech.smartnime.databinding.ActivitySidebarBinding
import id.smartech.smartnime.ui.main.MainActivity
import id.smartech.smartnime.ui.nav.InformationActivity
import id.smartech.smartnime.ui.nav.schedule.ScheduleActivity

class SidebarActivity : BaseActivity<ActivitySidebarBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_sidebar
        super.onCreate(savedInstanceState)

        setOnClick()
    }

    private fun setOnClick() {
        bind.information.setOnClickListener {
            intents<InformationActivity>(this)
        }

        bind.schedule.setOnClickListener {
            intents<ScheduleActivity>(this)
        }

        bind.back.setOnClickListener {
            intents<MainActivity>(this)
        }
    }
}