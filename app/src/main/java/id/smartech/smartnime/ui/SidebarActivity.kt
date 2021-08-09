package id.smartech.smartnime.ui

import android.os.Bundle
import id.smartech.smartnime.R
import id.smartech.smartnime.base.BaseActivity
import id.smartech.smartnime.databinding.ActivitySidebarBinding
import id.smartech.smartnime.ui.main.MainActivity
import id.smartech.smartnime.ui.nav.anime.ListAnimeActivity
import id.smartech.smartnime.ui.nav.information.InformationActivity
import id.smartech.smartnime.ui.nav.characters.TopCharactersActivity
import id.smartech.smartnime.ui.nav.schedule.ScheduleActivity
import id.smartech.smartnime.ui.nav.people.TopPeopleActivity
import id.smartech.smartnime.ui.nav.seasonal.SeasonalActivity

class SidebarActivity : BaseActivity<ActivitySidebarBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_sidebar
        super.onCreate(savedInstanceState)

        setOnClick()
    }

    private fun setOnClick() {
        bind.anime.setOnClickListener {
            intents<ListAnimeActivity>(this)
        }

        bind.schedule.setOnClickListener {
            intents<ScheduleActivity>(this)
        }

        bind.seasonal.setOnClickListener {
            intents<SeasonalActivity>(this)
        }

        bind.people.setOnClickListener {
            intents<TopPeopleActivity>(this)
        }

        bind.character.setOnClickListener {
            intents<TopCharactersActivity>(this)
        }

        bind.information.setOnClickListener {
            intents<InformationActivity>(this)
        }

        bind.back.setOnClickListener {
            intents<MainActivity>(this)
        }
    }
}