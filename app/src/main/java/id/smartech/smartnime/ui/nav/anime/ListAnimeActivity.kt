package id.smartech.smartnime.ui.nav.anime

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.smartech.smartnime.R
import id.smartech.smartnime.adapter.AnimeAdapter
import id.smartech.smartnime.base.BaseActivity
import id.smartech.smartnime.databinding.ActivityListAnimeBinding
import id.smartech.smartnime.model.TopAnimeModel
import id.smartech.smartnime.ui.sidebar.SidebarActivity

class ListAnimeActivity : BaseActivity<ActivityListAnimeBinding>() {
    private lateinit var viewModel: ListAnimeViewModel
    private lateinit var adapterAiring: AnimeAdapter
    private lateinit var adapterUpcoming: AnimeAdapter
    private lateinit var adapterPopular: AnimeAdapter
    private lateinit var adapterFavorites: AnimeAdapter
    private var listAiring = ArrayList<TopAnimeModel>()
    private var listUpcoming = ArrayList<TopAnimeModel>()
    private var listPopular = ArrayList<TopAnimeModel>()
    private var listFavorites = ArrayList<TopAnimeModel>()
    private lateinit var layoutManagerAiring: LinearLayoutManager
    private lateinit var layoutManagerUpcoming: LinearLayoutManager
    private lateinit var layoutManagerPopular: LinearLayoutManager
    private lateinit var layoutManagerFavorites: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_list_anime
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
        viewModel = ViewModelProvider(this).get(ListAnimeViewModel::class.java)
        viewModel.setService(createApi(this))
        viewModel.getAiringAnime()
        viewModel.getUpcomingAnime()
        callDelay()
    }

    private fun callDelay() {
        Handler().postDelayed({
            viewModel.getPopularAnime()
            viewModel.getFavoritesAnime()
        }, 2000)
    }

    private fun subscribeLiveData() {
        this.let {
            viewModel.isLoadingAiringLiveData.observe(it) { isLoading ->
                if(isLoading) {
                    bind.shimmerAiring.visibility = View.VISIBLE
                    bind.rvAiringAnime.visibility = View.GONE
                } else {
                    bind.shimmerAiring.stopShimmer()
                    bind.shimmerAiring.visibility = View.GONE
                    bind.rvAiringAnime.visibility = View.VISIBLE
                }
            }
        }

        this.let {
            viewModel.isLoadingUpcomingLiveData.observe(it) { isLoading ->
                if(isLoading) {
                    bind.shimmerUpcoming.visibility = View.VISIBLE
                    bind.rvUpcomingAnime.visibility = View.GONE
                } else {
                    bind.shimmerUpcoming.stopShimmer()
                    bind.shimmerUpcoming.visibility = View.GONE
                    bind.rvUpcomingAnime.visibility = View.VISIBLE
                }
            }
        }

        this.let {
            viewModel.isLoadingPopularLiveData.observe(it) { isLoading ->
                if(isLoading) {
                    bind.shimmerPopular.visibility = View.VISIBLE
                    bind.rvPopularAnime.visibility = View.GONE
                } else {
                    bind.shimmerPopular.stopShimmer()
                    bind.shimmerPopular.visibility = View.GONE
                    bind.rvPopularAnime.visibility = View.VISIBLE
                }
            }
        }

        this.let {
            viewModel.isLoadingFavoritesLiveData.observe(it) { isLoading ->
                if(isLoading) {
                    bind.shimmerFavorites.visibility = View.VISIBLE
                    bind.rvFavoritesAnime.visibility = View.GONE
                } else {
                    bind.shimmerFavorites.stopShimmer()
                    bind.shimmerFavorites.visibility = View.GONE
                    bind.rvFavoritesAnime.visibility = View.VISIBLE
                }
            }
        }

        this.let {
            viewModel.onSuccessAiringLiveData.observe(it) { listAiring ->
                adapterAiring.addList(listAiring)
            }
        }

        this.let {
            viewModel.onSuccessUpcomingLiveData.observe(it) { listUpcoming ->
                adapterUpcoming.addList(listUpcoming)
            }
        }

        this.let {
            viewModel.onSuccessPopularLiveData.observe(it) { listPopular ->
                adapterPopular.addList(listPopular)
            }
        }

        this.let {
            viewModel.onSuccessFavoritesLiveData.observe(it) { listFavorites ->
                adapterFavorites.addList(listFavorites)
            }
        }

        this.let {
            viewModel.onFailLiveData.observe(it) { message ->
                noticeToast(message)
            }
        }
    }

    private fun setRecyclerView() {
        layoutManagerAiring = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        layoutManagerUpcoming = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        layoutManagerPopular = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        layoutManagerFavorites = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)

        bind.rvAiringAnime.isNestedScrollingEnabled = false
        bind.rvAiringAnime.layoutManager = layoutManagerAiring
        adapterAiring = AnimeAdapter(listAiring)
        bind.rvAiringAnime.adapter = adapterAiring

        bind.rvUpcomingAnime.isNestedScrollingEnabled = false
        bind.rvUpcomingAnime.layoutManager = layoutManagerUpcoming
        adapterUpcoming = AnimeAdapter(listUpcoming)
        bind.rvUpcomingAnime.adapter = adapterUpcoming

        bind.rvPopularAnime.isNestedScrollingEnabled = false
        bind.rvPopularAnime.layoutManager = layoutManagerPopular
        adapterPopular = AnimeAdapter(listPopular)
        bind.rvPopularAnime.adapter = adapterPopular

        bind.rvFavoritesAnime.isNestedScrollingEnabled = false
        bind.rvFavoritesAnime.layoutManager = layoutManagerFavorites
        adapterFavorites = AnimeAdapter(listFavorites)
        bind.rvFavoritesAnime.adapter = adapterFavorites
    }
}