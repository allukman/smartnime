package id.smartech.smartnime.ui.detail.anime.episodes

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.smartech.smartnime.services.ApiServices
import id.smartech.smartnime.ui.detail.anime.episodes.model.EpisodesModel
import id.smartech.smartnime.ui.detail.anime.episodes.model.EpisodesResponse
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class AnimeEpisodesViewModel: ViewModel(), CoroutineScope {
    private lateinit var services: ApiServices

    val onSuccessLiveData = MutableLiveData<List<EpisodesModel>>()
    val onFailLiveData = MutableLiveData<String>()
    val isLoadingLiveData = MutableLiveData<Boolean>()

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setService(services: ApiServices) {
        this.services = services
    }

    fun getDetailAnimeEpisodes(id: Int) {
        launch {
            isLoadingLiveData.value = true

            val response = withContext(Dispatchers.IO) {
                try {
                    services.getDetailAnimeEpisodes(id)
                } catch (e: Throwable) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main){
                        isLoadingLiveData.value = false
                    }
                }
            }

            if (response is EpisodesResponse) {
                isLoadingLiveData.value = false
                onSuccessLiveData.value = response.episodes
            } else {
                onFailLiveData.value = "Get Data Failed"
            }
        }
    }
}