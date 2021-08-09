package id.smartech.smartnime.ui.nav.seasonal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.smartech.smartnime.R
import id.smartech.smartnime.adapter.SeasonalAnimeAdapter
import id.smartech.smartnime.adapter.TopAnimeAdapter
import id.smartech.smartnime.base.BaseActivity
import id.smartech.smartnime.databinding.ActivitySeasonalBinding
import id.smartech.smartnime.model.TopAnimeModel
import id.smartech.smartnime.ui.nav.schedule.ScheduleViewModel
import id.smartech.smartnime.ui.nav.seasonal.model.SeasonalAnimeModel

class SeasonalActivity : BaseActivity<ActivitySeasonalBinding>() {
    private lateinit var viewModel: SeasonalViewModel
    private lateinit var adapter: SeasonalAnimeAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private var list = ArrayList<SeasonalAnimeModel>()
    private var season: String? = null
    private var year: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_seasonal
        super.onCreate(savedInstanceState)

        setViewModel()
        subscribeLiveData()
        setRecyclerView()
        setSpinnerSeason()
        setSpinnerYear()
    }

    private fun setViewModel() {
        viewModel = ViewModelProvider(this).get(SeasonalViewModel::class.java)
        viewModel.setService(createApi(this))
    }

    private fun subscribeLiveData() {
        this.let {
            viewModel.isLoadingLiveData.observe(it) { isLoading ->
                if(isLoading) {
                    bind.progressBar.visibility = View.VISIBLE
                    bind.rvSeasonal.visibility = View.GONE
                    bind.line.visibility = View.GONE
                } else {
                    bind.progressBar.visibility = View.GONE
                    bind.rvSeasonal.visibility = View.VISIBLE
                    bind.line.visibility = View.VISIBLE
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

    private fun setRecyclerView() {
        bind.rvSeasonal.isNestedScrollingEnabled = false
        layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        bind.rvSeasonal.layoutManager = layoutManager
        adapter = SeasonalAnimeAdapter(list)
        bind.rvSeasonal.adapter = adapter
        adapter.setOnItemClickCallback(object : SeasonalAnimeAdapter.OnItemClickCallback{
            override fun onClickItem(data: SeasonalAnimeModel) {
                noticeToast(data.title)
            }
        })
    }

    private fun setSpinnerSeason() {
        val spinnerSeason = arrayOf("Summer", "Spring", "Fall", "Winter")
        bind.spinnerSeason.adapter = ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, spinnerSeason)
        bind.spinnerSeason.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when(spinnerSeason[position]) {
                    "Summer" -> {
                        season = "summer"
                    }
                    "Spring" -> {
                        season = "spring"
                    }
                    "Fall" -> {
                        season = "fall"
                    }
                    "Winter" -> {
                        season = "winter"
                    }
                }

                if(season != null && year != null) {
                    viewModel.getAnimeSeasonal(year.toString(),season.toString())
                }

            }
        }
    }

    private fun setSpinnerYear() {
        val spinnerYear : ArrayList<String> = ArrayList()
        for(i in 2021 downTo 1917) {
            spinnerYear.add(i.toString())
        }
        bind.spinnerYear.adapter = ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, spinnerYear)
        bind.spinnerYear.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                year = spinnerYear[position]
                if(season != null && year != null) {
                    viewModel.getAnimeSeasonal(year.toString(),season.toString())
                }
            }
        }
    }


}