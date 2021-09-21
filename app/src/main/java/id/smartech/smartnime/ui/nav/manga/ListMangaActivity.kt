package id.smartech.smartnime.ui.nav.manga

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.smartech.smartnime.R
import id.smartech.smartnime.adapter.AnimeAdapter
import id.smartech.smartnime.adapter.MangaAdapter
import id.smartech.smartnime.base.BaseActivity
import id.smartech.smartnime.databinding.ActivityListMangaBinding
import id.smartech.smartnime.model.TopMangaModel
import id.smartech.smartnime.ui.detail.manga.DetailMangaActivity
import id.smartech.smartnime.ui.nav.anime.ListAnimeViewModel
import id.smartech.smartnime.ui.sidebar.SidebarActivity

class ListMangaActivity : BaseActivity<ActivityListMangaBinding>() {
    private lateinit var viewModel: ListMangaViewModel
    private lateinit var adapter: MangaAdapter
    private lateinit var adapterFavorite: MangaAdapter
    private lateinit var adapterPopular: MangaAdapter
    private var list = ArrayList<TopMangaModel>()
    private var listFavorite = ArrayList<TopMangaModel>()
    private var listPopular = ArrayList<TopMangaModel>()
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var layoutManagerPopular: LinearLayoutManager
    private lateinit var layoutManagerFavorites: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_list_manga
        super.onCreate(savedInstanceState)

        setViewModel()
        subscribeLiveData()
        setSpinner()
        setRecyclerView()
        setOnClick()
    }

    private fun setOnClick() {
        bind.back.setOnClickListener {
            intents<SidebarActivity>(this)
        }
    }

    private fun setViewModel() {
        viewModel = ViewModelProvider(this).get(ListMangaViewModel::class.java)
        viewModel.setService(createApi(this))
        callDelay()
    }

    private fun callDelay() {
        Handler().postDelayed({
            viewModel.getPopularManga()
            viewModel.getFavoriteManga()
        }, 2000)
    }

    private fun subscribeLiveData() {
        this.let {
            viewModel.isLoadingCategoryLiveData.observe(it) { isLoading ->
                if(isLoading) {
                    bind.shimmer.visibility = View.VISIBLE
                    bind.rvCategoryManga.visibility = View.GONE
                } else {
                    bind.shimmer.stopShimmer()
                    bind.shimmer.visibility = View.GONE
                    bind.rvCategoryManga.visibility = View.VISIBLE
                }
            }
        }

        this.let {
            viewModel.isLoadingPopularLiveData.observe(it) { isLoading ->
                if(isLoading) {
                    bind.shimmerPopular.visibility = View.VISIBLE
                    bind.rvPopularManga.visibility = View.GONE
                } else {
                    bind.shimmerPopular.stopShimmer()
                    bind.shimmerPopular.visibility = View.GONE
                    bind.rvPopularManga.visibility = View.VISIBLE
                }
            }
        }

        this.let {
            viewModel.isLoadingFavoritesLiveData.observe(it) { isLoading ->
                if(isLoading) {
                    bind.shimmerFavorites.visibility = View.VISIBLE
                    bind.rvFavoritesManga.visibility = View.GONE
                } else {
                    bind.shimmerFavorites.stopShimmer()
                    bind.shimmerFavorites.visibility = View.GONE
                    bind.rvFavoritesManga.visibility = View.VISIBLE
                }
            }
        }

        this.let {
            viewModel.onSuccessCategoryLiveData.observe(it) { listCategory ->
                adapter.addList(listCategory)
            }
        }

        this.let {
            viewModel.onSuccessPopularLiveData.observe(it) { listPopular ->
                adapterPopular.addList(listPopular)
            }
        }

        this.let {
            viewModel.onSuccessFavoriteLiveData.observe(it) { listFavorites ->
                adapterFavorite.addList(listFavorites)
            }
        }

        this.let {
            viewModel.onFailLiveData.observe(it) { message ->
                noticeToast(message)
            }
        }
    }

    private fun setRecyclerView() {
        layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        layoutManagerPopular = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        layoutManagerFavorites = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)

        bind.rvCategoryManga.isNestedScrollingEnabled = false
        bind.rvCategoryManga.layoutManager = layoutManager
        adapter = MangaAdapter(list)
        bind.rvCategoryManga.adapter = adapter
        adapter.setOnItemClickCallback(object : MangaAdapter.OnItemClickCallback{
            override fun onClickItem(data: TopMangaModel) {
                val intent = Intent(this@ListMangaActivity, DetailMangaActivity::class.java)
                intent.putExtra("id", data.malId)
                startActivity(intent)
            }

        })

        bind.rvPopularManga.isNestedScrollingEnabled = false
        bind.rvPopularManga.layoutManager = layoutManagerPopular
        adapterPopular = MangaAdapter(listPopular)
        bind.rvPopularManga.adapter = adapterPopular
        adapterPopular.setOnItemClickCallback(object : MangaAdapter.OnItemClickCallback{
            override fun onClickItem(data: TopMangaModel) {
                val intent = Intent(this@ListMangaActivity, DetailMangaActivity::class.java)
                intent.putExtra("id", data.malId)
                startActivity(intent)
            }

        })

        bind.rvFavoritesManga.isNestedScrollingEnabled = false
        bind.rvFavoritesManga.layoutManager = layoutManagerFavorites
        adapterFavorite = MangaAdapter(listFavorite)
        bind.rvFavoritesManga.adapter = adapterFavorite
        adapterFavorite.setOnItemClickCallback(object : MangaAdapter.OnItemClickCallback{
            override fun onClickItem(data: TopMangaModel) {
                val intent = Intent(this@ListMangaActivity, DetailMangaActivity::class.java)
                intent.putExtra("id", data.malId)
                startActivity(intent)
            }

        })
    }

    private fun setSpinner() {
        val day = arrayOf("Manga", "Novels", "Oneshot", "Doujin", "Manhwa", "Manhua")
        bind.spinnerDay.adapter = ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, day)
        bind.spinnerDay.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (day[position]) {
                    "Manga" -> {
                        viewModel.getCategoryManga("manga")
                    }
                    "Novels" -> {
                        viewModel.getCategoryManga("novels")
                    }
                    "Oneshot" -> {
                        viewModel.getCategoryManga("oneshots")
                    }
                    "Doujin" -> {
                        viewModel.getCategoryManga("doujin")
                    }
                    "Manhwa" -> {
                        viewModel.getCategoryManga("manhwa")
                    }
                    "Manhua" -> {
                        viewModel.getCategoryManga("manhua")
                    }
                }
            }
        }
    }
}