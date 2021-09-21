package id.smartech.smartnime.ui.detail.anime

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import id.smartech.smartnime.R
import id.smartech.smartnime.adapter.AnimeCharacterAdapter
import id.smartech.smartnime.adapter.RecommendationsAdapter
import id.smartech.smartnime.base.BaseActivity
import id.smartech.smartnime.databinding.ActivityDetailAnimeBinding
import id.smartech.smartnime.model.RecommendationsModel
import id.smartech.smartnime.ui.detail.anime.episodes.AnimeEpisodesActivity
import id.smartech.smartnime.ui.detail.anime.model.DetailAnimeModel
import id.smartech.smartnime.ui.detail.anime.characters.AnimeCharacterModel
import id.smartech.smartnime.ui.detail.anime.model.VoiceActorModel
import id.smartech.smartnime.ui.detail.character.DetailCharacterActivity


class DetailAnimeActivity : BaseActivity<ActivityDetailAnimeBinding>() {
    private lateinit var viewModel: DetailAnimeViewModel
    private lateinit var adapter: AnimeCharacterAdapter
    private lateinit var adapterRecommendations: RecommendationsAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var layoutManagerRecommendations: LinearLayoutManager
    private var list = ArrayList<AnimeCharacterModel>()
    private var listRecommendations = ArrayList<RecommendationsModel>()
    private var id: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_detail_anime
        super.onCreate(savedInstanceState)

        id = intent.getIntExtra("id",0)

        setViewModel(id!!)
        subscribeLiveData()
        setRecyclerView()
        setOnClick()
    }

    private fun setViewModel(id: Int) {
        viewModel = ViewModelProvider(this).get(DetailAnimeViewModel::class.java)
        viewModel.setService(createApi(this))
        viewModel.getDetailAnime(id)
        viewModel.getDetailAnimeCharacter(id)
        viewModel.getDetailAnimeRecommendations(id)
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
                Log.d("recommendation", data.toString())
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
        adapter = AnimeCharacterAdapter(list)
        bind.rvCharacters.adapter = adapter
        adapter.setOnItemClickCallback(object: AnimeCharacterAdapter.OnItemClickCallback{
            override fun onClickCharacter(data: AnimeCharacterModel) {
                val intent = Intent(this@DetailAnimeActivity, DetailCharacterActivity::class.java)
                intent.putExtra("id", data.malId)
                startActivity(intent)
            }

            override fun onClickVoiceActor(data: VoiceActorModel) {
                intentBrowser(data.url!!)
            }


        })

        layoutManagerRecommendations = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)

        bind.rvRecommendations.isNestedScrollingEnabled = false
        bind.rvRecommendations.layoutManager = layoutManagerRecommendations
        adapterRecommendations = RecommendationsAdapter(listRecommendations)
        bind.rvRecommendations.adapter = adapterRecommendations

        adapterRecommendations.setOnItemClickCallback(object : RecommendationsAdapter.OnItemClickCallback {
            override fun onClickItem(data: RecommendationsModel) {
                val intent = Intent(this@DetailAnimeActivity, DetailAnimeActivity::class.java)
                intent.putExtra("id", data.malId)
                startActivity(intent)
            }

        })

    }
    private fun setData(data: DetailAnimeModel) {

        bind.title.text = data.title
        bind.toolbarTitle.text = data.title

        bind.rank.text = if (data.rank == null) "#N/A" else "#" + data.rank.toString()
        bind.popularity.text = if (data.popularity == null) "#N/A" else "#" + data.popularity.toString()
        bind.duration.text = data.duration
        bind.score.text = if (data.score == null) "N/A" else data.score.toString()
        bind.members.text = data.members.toString()
        bind.favorites.text = data.favorites.toString()
        bind.type.text = data.type
        bind.status.text = data.status
        bind.eps.text = if(data.episodes == null) "N/A" else data.episodes.toString() + " episodes"
        bind.englishTitle.text = data.titleEnglish
        bind.source.text = data.source ?: "N/A"
        bind.rating.text = data.rating!!.split(" ")[0]
        bind.season.text = data.premiered ?: "N/A"
        bind.aired.text = if (data.aired == null) "N/A" else data.aired.string
        bind.synopsisText.text = data.synopsis

        val genres = data.genres.map { it.name }
        val licensors = data.licensors?.map { it.name }
        val studios = data.studios?.map { it.name }

        bind.genres.text = if (genres.isNotEmpty()) removeBrackets(genres) else "N/A"
        bind.licensors.text = if (licensors!!.isNotEmpty()) removeBrackets(licensors) else "N/A"
        bind.studio.text = if (studios!!.isNotEmpty()) removeBrackets(studios) else "N/A"
        bind.btnEpisodes.text = if(data.episodes != null) "SEE ALL EPISODES(${data.episodes})" else "SEE ALL EPISODES"

        val opening = removeBrackets(data = data.openingThemes).replace(", ","\n")
        val ending = removeBrackets(data = data.endingThemes).replace(", ","\n")
        bind.opening.text = this.getString(R.string.opening, opening)
        bind.ending.text = this.getString(R.string.ending, ending)
        Glide.with(this)
                .load(data.imageUrl)
                .placeholder(R.drawable.white)
                .error(R.drawable.white)
                .into(bind.image)

        bind.detail.setOnClickListener {
            intentBrowser(url = data.url)
        }

        bind.share.setOnClickListener {
            shareToOtherApps(data.url)
        }

        if(data.trailerUrl == null) {
            bind.youtubePlayerView.visibility = View.GONE
        } else {
            bind.youtubePlayerView.visibility = View.VISIBLE
            val youtubeId = data.trailerUrl.split("/").last().split("?").first()
            bind.youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    youTubePlayer.loadVideo(youtubeId, 0f)
                }
            })
        }
    }

    private fun shareToOtherApps(text: String) {
        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.putExtra(Intent.EXTRA_TEXT, text)
        intent.type = "text/plain"

        startActivity(Intent.createChooser(intent, "share to : "))
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

    private fun intentBrowser(url: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
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

        bind.showOpening.setOnClickListener {
            bind.showOpening.visibility = View.GONE
            bind.hideOpening.visibility = View.VISIBLE
            bind.opening.visibility = View.VISIBLE
        }

        bind.hideOpening.setOnClickListener {
            bind.showOpening.visibility = View.VISIBLE
            bind.hideOpening.visibility = View.GONE
            bind.opening.visibility = View.GONE
        }

        bind.showEnding.setOnClickListener {
            bind.showEnding.visibility = View.GONE
            bind.hideEnding.visibility = View.VISIBLE
            bind.ending.visibility = View.VISIBLE
        }

        bind.hideEnding.setOnClickListener {
            bind.showEnding.visibility = View.VISIBLE
            bind.hideEnding.visibility = View.GONE
            bind.ending.visibility = View.GONE
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

        bind.btnEpisodes.setOnClickListener {
            val intent = Intent(this, AnimeEpisodesActivity::class.java)
            intent.putExtra("id", id)
            startActivity(intent)
        }
    }
}