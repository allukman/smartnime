package id.smartech.smartnime.ui.detail.manga

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.smartech.smartnime.model.RecommendationsModel
import id.smartech.smartnime.model.RecommendationsResponse
import id.smartech.smartnime.services.ApiServices
import id.smartech.smartnime.ui.detail.anime.characters.AnimeCharacterModel
import id.smartech.smartnime.ui.detail.anime.characters.AnimeCharacterResponse
import id.smartech.smartnime.ui.detail.manga.characters.MangaCharactersModel
import id.smartech.smartnime.ui.detail.manga.characters.MangaCharactersResponse
import id.smartech.smartnime.ui.detail.manga.model.DetailMangaResponse
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class DetailMangaViewModel: ViewModel(), CoroutineScope {

    private lateinit var services: ApiServices

    val onSuccessLiveData = MutableLiveData<DetailMangaResponse>()
    val onSuccessCharacterLiveData = MutableLiveData<List<MangaCharactersModel>>()
    val onSuccessRecommendationsLiveData = MutableLiveData<List<RecommendationsModel>>()
    val onFailLiveData = MutableLiveData<String>()
    val isLoadingLiveData = MutableLiveData<Boolean>()

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setService(services: ApiServices) {
        this.services = services
    }

    fun getDetailManga(id: Int) {
        launch {
            isLoadingLiveData.value = true

            val response = withContext(Dispatchers.IO) {
                try {
                    services.getDetailManga(id)
                } catch (e: Throwable) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main){
                        isLoadingLiveData.value = false
                    }
                }
            }

            if (response is DetailMangaResponse) {
                isLoadingLiveData.value = false
                onSuccessLiveData.value = response
            } else {
                onFailLiveData.value = "Get Data Failed"
            }
        }
    }

    fun getDetailMangaCharacter(id: Int) {
        launch {
            isLoadingLiveData.value = true

            val response = withContext(Dispatchers.IO) {
                try {
                    services.getDetailMangaCharacters(id)
                } catch (e: Throwable) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main){
                        isLoadingLiveData.value = false
                    }
                }
            }

            if (response is MangaCharactersResponse) {
                isLoadingLiveData.value = false
                onSuccessCharacterLiveData.value = response.character
            } else {
                onFailLiveData.value = "Get Data Failed"
            }
        }
    }

    fun getDetailMangaRecommendations(id: Int) {
        launch {
            isLoadingLiveData.value = true

            val response = withContext(Dispatchers.IO) {
                try {
                    services.getDetailMangaRecommendations(id)
                } catch (e: Throwable) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main){
                        isLoadingLiveData.value = false
                    }
                }
            }

            if (response is RecommendationsResponse) {
                isLoadingLiveData.value = false
                onSuccessRecommendationsLiveData.value = response.recommendations
            } else {
                onFailLiveData.value = "Get Data Failed"
            }
        }
    }
}