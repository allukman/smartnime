package id.smartech.smartnime.ui.detail.character

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.smartech.smartnime.R
import id.smartech.smartnime.adapter.*
import id.smartech.smartnime.base.BaseActivity
import id.smartech.smartnime.databinding.ActivityDetailCharacterBinding
import id.smartech.smartnime.ui.detail.anime.DetailAnimeActivity
import id.smartech.smartnime.ui.detail.character.model.AnimeographyModel
import id.smartech.smartnime.ui.detail.character.model.DetailCharacterResponse
import id.smartech.smartnime.ui.detail.character.model.VoiceActorModel
import id.smartech.smartnime.ui.detail.manga.DetailMangaActivity
import id.smartech.smartnime.ui.detail.manga.DetailMangaViewModel

class DetailCharacterActivity : BaseActivity<ActivityDetailCharacterBinding>() {
    private lateinit var viewModel: DetailCharacterViewModel
    private lateinit var adapterAnimeography: AnimeographyAdapter
    private lateinit var adapterMangaography: AnimeographyAdapter
    private lateinit var adapterVoiceActor: VoiceActorAdapter
    private lateinit var animeographyLayoutManager: LinearLayoutManager
    private lateinit var mangaographyLayoutManager: LinearLayoutManager
    private lateinit var voiceActorLayoutManager: LinearLayoutManager
    private var listAnimeography = ArrayList<AnimeographyModel>()
    private var listMangaography = ArrayList<AnimeographyModel>()
    private var listVoiceActor = ArrayList<VoiceActorModel>()

    private var id: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_detail_character
        super.onCreate(savedInstanceState)

        id = intent.getIntExtra("id",0)

        setViewModel(id!!)
        subscribeLiveData()
        setRecyclerView()

        val str = "Hello"
        // this
        str.run {
            println("The receiver string length: $length")
            //println("The receiver string length: ${this.length}") // does the same
        }

        // it
        str.let {
            println("The receiver string's length is ${it.length}")
        }

    }

    private fun setViewModel(id: Int) {
        viewModel = ViewModelProvider(this).get(DetailCharacterViewModel::class.java)
        viewModel.setService(createApi(this))
        viewModel.getDetailCharacter(id)
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
            viewModel.onSuccessAnimeographyLiveData.observe(it) { data ->
                adapterAnimeography.addList(data)
            }
        }

        this.let {
            viewModel.onSuccessMangaographyLiveData.observe(it) { data ->
                adapterMangaography.addList(data)
            }
        }

        this.let {
            viewModel.onSuccessVoiceActorLiveData.observe(it) { data ->
                adapterVoiceActor.addList(data)
            }
        }

        this.let {
            viewModel.onFailLiveData.observe(it) { message ->
                noticeToast(message)
            }
        }
    }

    private fun setData(data: DetailCharacterResponse) {
        val nickname = removeBrackets(data = data.nicknames).replace(", ","\n")

        bind.toolbarTitle.text = data.name
        bind.name.text = data.name
        bind.nameKanji.text = data.nameKanji
        bind.nickname.text = nickname
        bind.about.text = data.about

        bind.detail.setOnClickListener {
            intentBrowser(url = data.url)
        }

        bind.share.setOnClickListener {
            shareToOtherApps(data.url)
        }

        Glide.with(this)
            .load(data.imageUrl)
            .placeholder(R.drawable.white)
            .error(R.drawable.white)
            .into(bind.image)
    }

    private fun setRecyclerView() {
        animeographyLayoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)

        bind.rvAnimeography.isNestedScrollingEnabled = false
        bind.rvAnimeography.layoutManager = animeographyLayoutManager
        adapterAnimeography = AnimeographyAdapter(listAnimeography)
        bind.rvAnimeography.adapter = adapterAnimeography
        adapterAnimeography.setOnItemClickCallback(object : AnimeographyAdapter.OnItemClickCallback{
            override fun onClickItem(data: AnimeographyModel) {
                val intent = Intent(this@DetailCharacterActivity, DetailAnimeActivity::class.java)
                intent.putExtra("id", data.malId)
                startActivity(intent)
            }

        })

        mangaographyLayoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)

        bind.rvMangaography.isNestedScrollingEnabled = false
        bind.rvMangaography.layoutManager = mangaographyLayoutManager
        adapterMangaography = AnimeographyAdapter(listMangaography)
        bind.rvMangaography.adapter = adapterMangaography
        adapterMangaography.setOnItemClickCallback(object : AnimeographyAdapter.OnItemClickCallback{
            override fun onClickItem(data: AnimeographyModel) {
                val intent = Intent(this@DetailCharacterActivity, DetailMangaActivity::class.java)
                intent.putExtra("id", data.malId)
                startActivity(intent)
            }

        })

        voiceActorLayoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)

        bind.rvVoiceActor.isNestedScrollingEnabled = false
        bind.rvVoiceActor.layoutManager = voiceActorLayoutManager
        adapterVoiceActor = VoiceActorAdapter(listVoiceActor)
        bind.rvVoiceActor.adapter = adapterVoiceActor
        adapterVoiceActor.setOnItemClickCallback(object : VoiceActorAdapter.OnItemClickCallback{
            override fun onClickItem(data: VoiceActorModel) {
                intentBrowser(data.url)
            }

        })
    }

    private fun intentBrowser(url: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    }

    private fun shareToOtherApps(text: String) {
        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.putExtra(Intent.EXTRA_TEXT, text)
        intent.type = "text/plain"

        startActivity(Intent.createChooser(intent, "share to : "))
    }

    private fun removeBrackets(data: List<String>?) : String{
        return if (data.isNullOrEmpty()) {
            ""
        } else {
            var result = ""
            for(i in data.toString()) {
                if(i == data.toString().first() || i == data.toString().last()) {
                    continue
                } else {
                    result += i.toString()
                }
            }

            result
        }

    }
}