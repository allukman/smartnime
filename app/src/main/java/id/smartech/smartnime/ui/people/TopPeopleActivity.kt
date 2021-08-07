package id.smartech.smartnime.ui.people

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import id.smartech.smartnime.R
import id.smartech.smartnime.adapter.ScheduleAnimeAdapter
import id.smartech.smartnime.adapter.TopPeopleAdapter
import id.smartech.smartnime.base.BaseActivity
import id.smartech.smartnime.databinding.ActivityTopPeopleBinding
import id.smartech.smartnime.ui.SidebarActivity
import id.smartech.smartnime.ui.main.MainViewModel
import id.smartech.smartnime.ui.nav.schedule.model.DayModel
import id.smartech.smartnime.ui.people.model.TopPeopleModel

class TopPeopleActivity : BaseActivity<ActivityTopPeopleBinding>() {
    private lateinit var viewModel: TopPeopleViewModel
    private lateinit var adapter: TopPeopleAdapter
    private lateinit var layoutManager: GridLayoutManager
    private var list = ArrayList<TopPeopleModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_top_people
        super.onCreate(savedInstanceState)

        setViewModel()
        subscribeLiveData()
        setRecyclerView()
        setOnClick()
    }

    private fun setOnClick() {
        bind.back.setOnClickListener {
            intents<SidebarActivity>(this)
        }
    }

    private fun setViewModel() {
        viewModel = ViewModelProvider(this).get(TopPeopleViewModel::class.java)
        viewModel.setService(createApi(this))
        viewModel.getTopAnime()
    }

    private fun subscribeLiveData() {
        this.let {
            viewModel.isLoadingLiveData.observe(it) { isLoading ->
                if(isLoading) {
                    bind.progressBar.visibility = View.VISIBLE
                    bind.rvTopPeople.visibility = View.GONE
                } else {
                    bind.progressBar.visibility = View.GONE
                    bind.rvTopPeople.visibility = View.VISIBLE
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
        bind.rvTopPeople.isNestedScrollingEnabled = false
        layoutManager = GridLayoutManager(applicationContext, 2, LinearLayoutManager.VERTICAL, false)
        bind.rvTopPeople.layoutManager = layoutManager
        adapter = TopPeopleAdapter(list)
        bind.rvTopPeople.adapter = adapter
    }

}