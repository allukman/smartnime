package id.smartech.smartnime.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.smartech.smartnime.model.TopAnimeModel
import id.smartech.smartnime.model.TopAnimeResponse
import id.smartech.smartnime.model.TopMangaModel
import id.smartech.smartnime.model.TopMangaResponse
import id.smartech.smartnime.services.ApiServices
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainViewModel: ViewModel(), CoroutineScope {
    private lateinit var services: ApiServices

    val onSuccessAnimeLiveData = MutableLiveData<List<TopAnimeModel>>()
    val onSuccessMangaLiveData = MutableLiveData<List<TopMangaModel>>()
    val onFailLiveData = MutableLiveData<String>()
    val isLoadingAnimeLiveData = MutableLiveData<Boolean>()
    val isLoadingMangaLiveData = MutableLiveData<Boolean>()

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setService(services: ApiServices) {
        this.services = services
    }

    fun getTopAnime() {
        launch {
            isLoadingAnimeLiveData.value = true

            val response = withContext(Dispatchers.IO) {
                try {
                    services.getTopAnime(1)
                } catch (e: Throwable) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main){
                        isLoadingAnimeLiveData.value = false
                    }
                }
            }

            if (response is TopAnimeResponse) {
                isLoadingAnimeLiveData.value = false

                val list = response.top.map {
                    TopAnimeModel(
                            malId = it.malId,
                            rank = it.rank,
                            imageUrl = it.imageUrl,
                            title = it.title,
                            startDate = it.startDate,
                            type = it.type,
                            endDate = it.endDate,
                            episodes = it.episodes,
                            members = it.members,
                            score = it.score,
                            url = it.url
                    )
                }
                onSuccessAnimeLiveData.value = list
            } else {
                onFailLiveData.value = "Get Data Failed"
            }
        }
    }

    fun getTopManga() {
        launch {
            isLoadingMangaLiveData.value = true

            val response = withContext(Dispatchers.IO) {
                try {
                    services.getTopManga(1)
                } catch (e: Throwable) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main){
                        isLoadingMangaLiveData.value = false
                    }
                }
            }

            if (response is TopMangaResponse) {
                isLoadingMangaLiveData.value = false

                val list = response.top.map {
                    TopMangaModel(
                            malId = it.malId,
                            rank = it.rank,
                            imageUrl = it.imageUrl,
                            title = it.title,
                            startDate = it.startDate,
                            type = it.type,
                            endDate = it.endDate,
                            volumes = it.volumes,
                            members = it.members,
                            score = it.score,
                            url = it.imageUrl
                    )
                }
                onSuccessMangaLiveData.value = list
            } else {
                onFailLiveData.value = "Get Data Failed"
            }
        }
    }
}