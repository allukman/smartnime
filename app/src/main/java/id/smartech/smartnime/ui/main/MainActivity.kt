package id.smartech.smartnime.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.smartech.smartnime.R
import id.smartech.smartnime.adapter.TopAnimeAdapter
import id.smartech.smartnime.adapter.TopMangaAdapter
import id.smartech.smartnime.base.BaseActivity
import id.smartech.smartnime.databinding.ActivityMainBinding
import id.smartech.smartnime.model.TopAnimeModel
import id.smartech.smartnime.model.TopMangaModel
import id.smartech.smartnime.ui.sidebar.SidebarActivity
import id.smartech.smartnime.ui.search.SearchActivity
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : BaseActivity<ActivityMainBinding>() {
    private lateinit var viewModel: MainViewModel
    private lateinit var adapterAnime: TopAnimeAdapter
    private lateinit var adapterManga: TopMangaAdapter
    private lateinit var layoutManagerHorizontal: LinearLayoutManager
    private lateinit var layoutManagerVertikal: LinearLayoutManager
    private var listAnime = ArrayList<TopAnimeModel>()
    private var listManga = ArrayList<TopMangaModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_main
        super.onCreate(savedInstanceState)

        startShimmer()
        setViewModel()
        subscribeLiveData()
        setRecyclerView()
        setTime()
        setOnClick()
        setSearchView()
    }

    private fun setRecyclerView() {
        bind.rvTopAnime.isNestedScrollingEnabled = false
        layoutManagerHorizontal = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        bind.rvTopAnime.layoutManager = layoutManagerHorizontal
        adapterAnime = TopAnimeAdapter(listAnime)
        bind.rvTopAnime.adapter = adapterAnime
        adapterAnime.setOnItemClickCallback(object : TopAnimeAdapter.OnItemClickCallback{
            override fun onClickItem(data: TopAnimeModel) {
                noticeToast(data.title)
            }
        })

        bind.rvTopManga.isNestedScrollingEnabled = false
        layoutManagerVertikal = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        bind.rvTopManga.layoutManager = layoutManagerVertikal
        adapterManga = TopMangaAdapter(listManga)
        bind.rvTopManga.adapter = adapterManga
        adapterManga.setOnItemClickCallback(object : TopMangaAdapter.OnItemClickCallback{
            override fun onClickItem(data: TopMangaModel) {
                noticeToast(data.title)
            }
        })
    }

    private fun setOnClick() {
        bind.hamburger.setOnClickListener {
            intents<SidebarActivity>(this)
        }
    }

    private fun setSearchView() {
        bind.searchButton.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener,
                SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val intent = Intent(this@MainActivity, SearchActivity::class.java)
                intent.putExtra("key", query)
                startActivity(intent)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun setTime() {
        val c: Calendar = Calendar.getInstance()
        val timeOfDay: Int = c.get(Calendar.HOUR_OF_DAY)

        bind.greeting.text = when (timeOfDay) {
            in 0..11 -> {
                "Good Morning"
            }
            in 12..15 -> {
                "Good Evening"
            }
            in 16..20 -> {
                "Good Afternoon"
            }
            in 21..23 -> {
                "Good Night"
            }
            else -> {
                "Good Morning"
            }
        }
    }

    private fun setViewModel() {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.setService(createApi(this))
        viewModel.getTopAnime()
        viewModel.getTopManga()
    }

    private fun startShimmer() {
        bind.shimmerLayout.startShimmer()
        bind.shimmerLayoutManga.startShimmer()
    }

    private fun subscribeLiveData() {
        this.let {
            viewModel.isLoadingAnimeLiveData.observe(it) { isLoading ->
                if(isLoading) {
                    bind.shimmerLayout.visibility = View.VISIBLE
                } else {
                    bind.shimmerLayout.stopShimmer()
                    bind.shimmerLayout.visibility = View.GONE
                    bind.rvTopAnime.visibility = View.VISIBLE
                }
            }
        }

        this.let {
            viewModel.isLoadingMangaLiveData.observe(it) { isLoading ->
                if(isLoading) {
                    bind.shimmerLayoutManga.visibility = View.VISIBLE
                } else {
                    bind.shimmerLayoutManga.stopShimmer()
                    bind.shimmerLayoutManga.visibility = View.GONE
                    bind.rvTopManga.visibility = View.VISIBLE
                }
            }
        }

        this.let {
            viewModel.onSuccessAnimeLiveData.observe(it) { list ->
                adapterAnime.addList(list)
            }
        }

        this.let {
            viewModel.onSuccessMangaLiveData.observe(it) { list ->
                adapterManga.addList(list)
            }
        }

        this.let {
            viewModel.onFailLiveData.observe(it) { message ->
                noticeToast(message)
            }
        }
    }
}