package id.smartech.smartnime.ui.nav.manga

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.smartech.smartnime.model.TopAnimeModel
import id.smartech.smartnime.model.TopAnimeResponse
import id.smartech.smartnime.model.TopMangaModel
import id.smartech.smartnime.model.TopMangaResponse
import id.smartech.smartnime.services.ApiServices
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class ListMangaViewModel: ViewModel(), CoroutineScope {
    private lateinit var services: ApiServices

    val onSuccessCategoryLiveData = MutableLiveData<List<TopMangaModel>>()
    val onSuccessPopularLiveData = MutableLiveData<List<TopMangaModel>>()
    val onSuccessFavoriteLiveData = MutableLiveData<List<TopMangaModel>>()
    val onFailLiveData = MutableLiveData<String>()
    val isLoadingCategoryLiveData = MutableLiveData<Boolean>()
    val isLoadingPopularLiveData = MutableLiveData<Boolean>()
    val isLoadingFavoritesLiveData = MutableLiveData<Boolean>()

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setService(services: ApiServices) {
        this.services = services
    }

    fun getCategoryManga(category: String) {
        launch {
            isLoadingCategoryLiveData.value = true

            val response = withContext(Dispatchers.IO) {
                try {
                    services.getMangaCategory(category)
                } catch (e: Throwable) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main){
                        isLoadingCategoryLiveData.value = false
                    }
                }
            }

            if (response is TopMangaResponse) {
                isLoadingCategoryLiveData.value = false

                val list = response.top.map {
                    TopMangaModel(
                        malId = it.malId,
                        rank = it.rank,
                        imageUrl = it.imageUrl,
                        title = it.title,
                        startDate = it.startDate,
                        type = it.type,
                        endDate = it.endDate,
                        members = it.members,
                        score = it.score,
                        url = it.url,
                        volumes = it.volumes
                    )
                }
                onSuccessCategoryLiveData.value = list
            } else {
                onFailLiveData.value = "Get Data Failed"
            }
        }
    }

    fun getPopularManga() {
        launch {
            isLoadingPopularLiveData.value = true

            val response = withContext(Dispatchers.IO) {
                try {
                    services.getPopularManga()
                } catch (e: Throwable) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main){
                        isLoadingPopularLiveData.value = false
                    }
                }
            }

            if (response is TopMangaResponse) {
                isLoadingPopularLiveData.value = false

                val list = response.top.map {
                    TopMangaModel(
                        malId = it.malId,
                        rank = it.rank,
                        imageUrl = it.imageUrl,
                        title = it.title,
                        startDate = it.startDate,
                        type = it.type,
                        endDate = it.endDate,
                        members = it.members,
                        score = it.score,
                        url = it.url,
                        volumes = it.volumes
                    )
                }
                onSuccessPopularLiveData.value = list
            } else {
                onFailLiveData.value = "Get Data Failed"
            }
        }
    }

    fun getFavoriteManga() {
        launch {
            isLoadingFavoritesLiveData.value = true

            val response = withContext(Dispatchers.IO) {
                try {
                    services.getFavoriteManga()
                } catch (e: Throwable) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main){
                        isLoadingFavoritesLiveData.value = false
                    }
                }
            }

            if (response is TopMangaResponse) {
                isLoadingFavoritesLiveData.value = false

                val list = response.top.map {
                    TopMangaModel(
                        malId = it.malId,
                        rank = it.rank,
                        imageUrl = it.imageUrl,
                        title = it.title,
                        startDate = it.startDate,
                        type = it.type,
                        endDate = it.endDate,
                        members = it.members,
                        score = it.score,
                        url = it.url,
                        volumes = it.volumes
                    )
                }
                onSuccessFavoriteLiveData.value = list
            } else {
                onFailLiveData.value = "Get Data Failed"
            }
        }
    }
}