package id.smartech.smartnime.ui.nav.schedule

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.GridLayout
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.smartech.smartnime.R
import id.smartech.smartnime.adapter.ScheduleAnimeAdapter
import id.smartech.smartnime.adapter.TopAnimeAdapter
import id.smartech.smartnime.base.BaseActivity
import id.smartech.smartnime.databinding.ActivityScheduleBinding
import id.smartech.smartnime.model.TopMangaModel
import id.smartech.smartnime.ui.SidebarActivity
import id.smartech.smartnime.ui.main.MainViewModel
import id.smartech.smartnime.ui.nav.schedule.model.DayModel

class ScheduleActivity : BaseActivity<ActivityScheduleBinding>() {
    private lateinit var viewModel: ScheduleViewModel
    private lateinit var adapter: ScheduleAnimeAdapter
    private lateinit var layoutManager: GridLayoutManager
    private var list = ArrayList<DayModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_schedule
        super.onCreate(savedInstanceState)


        setViewModel()
        subscribeLiveData()
        setRecyclerView()
        setSpinner()
        setOnClick()
    }

    private fun setOnClick() {
        bind.back.setOnClickListener {
            intents<SidebarActivity>(this)
        }
    }

    private fun setRecyclerView() {
        bind.rvSchedule.isNestedScrollingEnabled = false
        layoutManager = GridLayoutManager(applicationContext, 2, LinearLayoutManager.VERTICAL, false)
        bind.rvSchedule.layoutManager = layoutManager
        adapter = ScheduleAnimeAdapter(list)
        bind.rvSchedule.adapter = adapter
    }

    private fun setViewModel() {
        viewModel = ViewModelProvider(this).get(ScheduleViewModel::class.java)
        viewModel.setService(createApi(this))
    }

    private fun subscribeLiveData() {
        this.let {
            viewModel.isLoadingLiveData.observe(it) { isLoading ->
                if(isLoading) {
                    bind.progressBar.visibility = View.VISIBLE
                    bind.rvSchedule.visibility = View.GONE
                } else {
                    bind.progressBar.visibility = View.GONE
                    bind.rvSchedule.visibility = View.VISIBLE
                }
            }
        }

        this.let {
            viewModel.onSuccessLiveData.observe(it) { list ->
                adapter.addList(list)
            }
        }

        this.let {
            viewModel.onFailLiveData.observe(it) { message ->
                noticeToast(message)
            }
        }
    }

    private fun setSpinner() {
        val day = arrayOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday", "Other", "Unknown")
        bind.spinnerDay.adapter = ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, day)
        bind.spinnerDay.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (day[position]) {
                    "Sunday" -> {
                        viewModel.getSundaySchedule()
                    }
                    "Monday" -> {
                        viewModel.getMondaySchedule()
                    }
                    "Tuesday" -> {
                        viewModel.getTuesdaySchedule()
                    }
                    "Wednesday" -> {
                        viewModel.getWednesdaySchedule()
                    }
                    "Thursday" -> {
                        viewModel.getThursdaySchedule()
                    }
                    "Friday" -> {
                        viewModel.getFridaySchedule()
                    }
                    "Saturday" -> {
                        viewModel.getSaturdaySchedule()
                    }
                    "Other" -> {
                        viewModel.getOtherDaySchedule()
                    }
                    "Unknown" -> {
                        viewModel.getUnknownDaySchedule()
                    }
                }
            }
        }
    }
}