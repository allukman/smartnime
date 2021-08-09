package id.smartech.smartnime.ui.nav.anime

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.smartech.smartnime.model.TopAnimeModel
import id.smartech.smartnime.model.TopAnimeResponse
import id.smartech.smartnime.model.TopMangaModel
import id.smartech.smartnime.services.ApiServices
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class ListAnimeViewModel: ViewModel(), CoroutineScope {
    private lateinit var services: ApiServices

    val onSuccessAiringLiveData = MutableLiveData<List<TopAnimeModel>>()
    val onSuccessUpcomingLiveData = MutableLiveData<List<TopAnimeModel>>()
    val onSuccessPopularLiveData = MutableLiveData<List<TopAnimeModel>>()
    val onSuccessFavoritesLiveData = MutableLiveData<List<TopAnimeModel>>()
    val onSuccessTopLiveData = MutableLiveData<List<TopAnimeModel>>()
    val onFailLiveData = MutableLiveData<String>()
    val isLoadingAiringLiveData = MutableLiveData<Boolean>()
    val isLoadingUpcomingLiveData = MutableLiveData<Boolean>()
    val isLoadingPopularLiveData = MutableLiveData<Boolean>()
    val isLoadingFavoritesLiveData = MutableLiveData<Boolean>()
    val isLoadingTopLiveData = MutableLiveData<Boolean>()

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setService(services: ApiServices) {
        this.services = services
    }

    fun getAiringAnime() {
        launch {
            isLoadingAiringLiveData.value = true

            val response = withContext(Dispatchers.IO) {
                try {
                    services.getAiringAnime()
                } catch (e: Throwable) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main){
                        isLoadingAiringLiveData.value = false
                    }
                }
            }

            if (response is TopAnimeResponse) {
                isLoadingAiringLiveData.value = false

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
                onSuccessAiringLiveData.value = list
            } else {
                onFailLiveData.value = "Get Data Failed"
            }
        }
    }

    fun getUpcomingAnime() {
        launch {
            isLoadingUpcomingLiveData.value = true

            val response = withContext(Dispatchers.IO) {
                try {
                    services.getUpcomingAnime()
                } catch (e: Throwable) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main){
                        isLoadingUpcomingLiveData.value = false
                    }
                }
            }

            if (response is TopAnimeResponse) {
                isLoadingUpcomingLiveData.value = false

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
                onSuccessUpcomingLiveData.value = list
            } else {
                onFailLiveData.value = "Get Data Failed"
            }
        }
    }

    fun getPopularAnime() {
        launch {
            isLoadingPopularLiveData.value = true

            val response = withContext(Dispatchers.IO) {
                try {
                    services.getPopularAnime()
                } catch (e: Throwable) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main){
                        isLoadingPopularLiveData.value = false
                    }
                }
            }

            if (response is TopAnimeResponse) {
                isLoadingPopularLiveData.value = false

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
                onSuccessPopularLiveData.value = list
            } else {
                onFailLiveData.value = "Get Data Failed"
            }
        }
    }

    fun getFavoritesAnime() {
        launch {
            isLoadingFavoritesLiveData.value = true

            val response = withContext(Dispatchers.IO) {
                try {
                    services.getFavoriteAnime()
                } catch (e: Throwable) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main){
                        isLoadingFavoritesLiveData.value = false
                    }
                }
            }

            if (response is TopAnimeResponse) {
                isLoadingFavoritesLiveData.value = false

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
                onSuccessFavoritesLiveData.value = list
            } else {
                onFailLiveData.value = "Get Data Failed"
            }
        }
    }

    fun getTopAnime() {
        launch {
            isLoadingTopLiveData.value = true

            val response = withContext(Dispatchers.IO) {
                try {
                    services.getTopAnime()
                } catch (e: Throwable) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main){
                        isLoadingTopLiveData.value = false
                    }
                }
            }

            if (response is TopAnimeResponse) {
                isLoadingTopLiveData.value = false

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
                onSuccessTopLiveData.value = list
            } else {
                onFailLiveData.value = "Get Data Failed"
            }
        }
    }
}