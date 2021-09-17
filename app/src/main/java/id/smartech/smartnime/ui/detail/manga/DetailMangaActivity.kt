package id.smartech.smartnime.ui.detail.manga

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.smartech.smartnime.R
import id.smartech.smartnime.adapter.AnimeCharacterAdapter
import id.smartech.smartnime.adapter.MangaAdapter
import id.smartech.smartnime.adapter.MangaCharacterAdapter
import id.smartech.smartnime.adapter.RecommendationsAdapter
import id.smartech.smartnime.base.BaseActivity
import id.smartech.smartnime.databinding.ActivityDetailMangaBinding
import id.smartech.smartnime.model.RecommendationsModel
import id.smartech.smartnime.ui.detail.anime.DetailAnimeViewModel
import id.smartech.smartnime.ui.detail.anime.characters.AnimeCharacterModel
import id.smartech.smartnime.ui.detail.anime.episodes.AnimeEpisodesActivity
import id.smartech.smartnime.ui.detail.manga.characters.MangaCharactersModel
import id.smartech.smartnime.ui.detail.manga.model.DetailMangaResponse

class DetailMangaActivity : BaseActivity<ActivityDetailMangaBinding>() {
    private lateinit var viewModel: DetailMangaViewModel
    private lateinit var adapter: MangaCharacterAdapter
    private lateinit var adapterRecommendations: RecommendationsAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var layoutManagerRecommendations: LinearLayoutManager
    private var list = ArrayList<MangaCharactersModel>()
    private var listRecommendations = ArrayList<RecommendationsModel>()
    private var id: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_detail_manga
        super.onCreate(savedInstanceState)

        id = intent.getIntExtra("id",0)

        setViewModel(id!!)
        subscribeLiveData()
        setRecyclerView()
        setOnClick()
    }

    private fun setViewModel(id: Int) {
        viewModel = ViewModelProvider(this).get(DetailMangaViewModel::class.java)
        viewModel.setService(createApi(this))
        viewModel.getDetailManga(id)
        viewModel.getDetailMangaCharacter(id)
        viewModel.getDetailMangaRecommendations(id)
    }

    private fun subscribeLiveData() {
        this.let {
            viewModel.isLoadingLiveData.observe(it) { isLoading ->
                if(isLoading) {
                    bind.container.visibility = View.GONE
                    bind.progressBar.visibility = View.VISIBLE
                } else {
                    bind.container.visibility = View.VISIBLE
                    bind.progressBar.visibility = View.GONE
                }
            }
        }

        this.let {
            viewModel.onSuccessLiveData.observe(it) { data ->
                setData(data)
            }
        }

        this.let {
            viewModel.onSuccessCharacterLiveData.observe(it) { data ->
                adapter.addList(data)
            }
        }

        this.let {
            viewModel.onSuccessRecommendationsLiveData.observe(it) { data ->
                adapterRecommendations.addList(data)
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

        bind.rvCharacters.isNestedScrollingEnabled = false
        bind.rvCharacters.layoutManager = layoutManager
        adapter = MangaCharacterAdapter(list)
        bind.rvCharacters.adapter = adapter

        layoutManagerRecommendations = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)

        bind.rvRecommendations.isNestedScrollingEnabled = false
        bind.rvRecommendations.layoutManager = layoutManagerRecommendations
        adapterRecommendations = RecommendationsAdapter(listRecommendations)
        bind.rvRecommendations.adapter = adapterRecommendations

    }

    private fun setData(data: DetailMangaResponse) {

        bind.title.text = data.title
        bind.toolbarTitle.text = data.title
        bind.rank.text = if (data.rank == null) "#N/A" else "#" + data.rank.toString()
        bind.popularity.text = if (data.popularity == null) "#N/A" else "#" + data.popularity.toString()
        bind.score.text = if (data.score == null) "N/A" else data.score.toString()
        bind.members.text = data.members.toString()
        bind.favorites.text = data.favorites.toString()
        bind.status.text = data.status
        bind.synopsisText.text = data.synopsis
        bind.englishTitle.text = data.titleEnglish
        bind.volume.text = data.volumes.toString()
        bind.chapter.text = data.chapters.toString()

        val genres = data.genres.map { it.name.replace("\n                ","") }
        bind.genres.text = if (genres.isNotEmpty()) removeBrackets(genres) else "N/A"

        Glide.with(this)
                .load(data.imageUrl)
                .placeholder(R.drawable.white)
                .error(R.drawable.white)
                .into(bind.image)
    }

    private fun removeBrackets(data: List<String>) : String{
        var result = ""

        for(i in data.toString()) {
            if(i == data.toString().first() || i == data.toString().last()) {
                continue
            } else {
                result += i.toString()
            }
        }

        return result
    }

    private fun setOnClick() {
        bind.showSynopsis.setOnClickListener {
            bind.showSynopsis.visibility = View.GONE
            bind.hideSynopsis.visibility = View.VISIBLE
            bind.synopsisText.visibility = View.VISIBLE
        }

        bind.hideSynopsis.setOnClickListener {
            bind.showSynopsis.visibility = View.VISIBLE
            bind.hideSynopsis.visibility = View.GONE
            bind.synopsisText.visibility = View.GONE
        }

        bind.back.setOnClickListener {
            onBackPressed()
        }

        bind.favorite.setOnClickListener {
            bind.favorite.visibility = View.GONE
            bind.unfavorite.visibility = View.VISIBLE
        }

        bind.unfavorite.setOnClickListener {
            bind.unfavorite.visibility = View.GONE
            bind.favorite.visibility = View.VISIBLE
        }

    }
}