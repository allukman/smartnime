package id.smartech.smartnime.ui.detail.anime.episodes

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.smartech.smartnime.R
import id.smartech.smartnime.adapter.EpisodesAdapter
import id.smartech.smartnime.base.BaseActivity
import id.smartech.smartnime.databinding.ActivityAnimeEpisodesBinding
import id.smartech.smartnime.ui.detail.anime.episodes.model.EpisodesModel

class AnimeEpisodesActivity : BaseActivity<ActivityAnimeEpisodesBinding>() {
    private lateinit var viewModel: AnimeEpisodesViewModel
    private lateinit var adapter: EpisodesAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private var list = ArrayList<EpisodesModel>()
    private var id: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_anime_episodes
        super.onCreate(savedInstanceState)

        id = intent.getIntExtra("id",0)

        setViewModel(id!!)
        subscribeLiveData()
        setRecyclerView()
        setOnClick()
    }

    private fun setOnClick(){
        bind.back.setOnClickListener { onBackPressed() }
    }

    private fun setViewModel(id: Int) {
        viewModel = ViewModelProvider(this).get(AnimeEpisodesViewModel::class.java)
        viewModel.setService(createApi(this))
        viewModel.getDetailAnimeEpisodes(id)
    }

    private fun subscribeLiveData() {
        this.let {
            viewModel.isLoadingLiveData.observe(it) { isLoading ->
                if(isLoading) {
                    bind.progressBar.visibility = View.VISIBLE
                } else {
                    bind.progressBar.visibility = View.GONE
                }
            }
        }

        this.let {
            viewModel.onSuccessLiveData.observe(it) { data ->
                if (data.isNullOrEmpty()) {
                    bind.lottieNotFound.visibility = View.VISIBLE
                    bind.tvNotFound.visibility = View.VISIBLE
                } else {
                    adapter.addList(data)
                    bind.lottieNotFound.visibility = View.GONE
                    bind.tvNotFound.visibility = View.GONE
                }
            }
        }

        this.let {
            viewModel.onFailLiveData.observe(it) { message ->
                noticeToast(message)
            }
        }
    }

    private fun setRecyclerView() {
        layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        bind.rvEpisodes.isNestedScrollingEnabled = false
        bind.rvEpisodes.layoutManager = layoutManager
        adapter = EpisodesAdapter(list)
        bind.rvEpisodes.adapter = adapter

        adapter.setOnItemClickCallback(object : EpisodesAdapter.OnItemClickCallback{
            override fun onClickItem(data: EpisodesModel) {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(data.videoUrl))
                startActivity(browserIntent)
            }

        })

    }
}