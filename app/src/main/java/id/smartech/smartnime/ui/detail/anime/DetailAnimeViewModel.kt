package id.smartech.smartnime.ui.detail.anime

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.smartech.smartnime.model.RecommendationsModel
import id.smartech.smartnime.model.RecommendationsResponse
import id.smartech.smartnime.services.ApiServices
import id.smartech.smartnime.ui.detail.anime.characters.AnimeCharacterResponse
import id.smartech.smartnime.ui.detail.anime.model.DetailAnimeModel
import id.smartech.smartnime.ui.detail.anime.characters.AnimeCharacterModel
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class DetailAnimeViewModel: ViewModel(), CoroutineScope {
    private lateinit var services: ApiServices

    val onSuccessLiveData = MutableLiveData<DetailAnimeModel>()
    val onSuccessCharacterLiveData = MutableLiveData<List<AnimeCharacterModel>>()
    val onSuccessRecommendationsLiveData = MutableLiveData<List<RecommendationsModel>>()
    val onFailLiveData = MutableLiveData<String>()
    val isLoadingLiveData = MutableLiveData<Boolean>()

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setService(services: ApiServices) {
        this.services = services
    }

    fun getDetailAnime(id: Int) {
        launch {
            isLoadingLiveData.value = true

            val response = withContext(Dispatchers.IO) {
                try {
                    services.getDetailAnime(id)
                } catch (e: Throwable) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main){
                        isLoadingLiveData.value = false
                    }
                }
            }

            if (response is DetailAnimeModel) {
                isLoadingLiveData.value = false
                onSuccessLiveData.value = response
            } else {
                onFailLiveData.value = "Get Data Failed"
            }
        }
    }

    fun getDetailAnimeCharacter(id: Int) {
        launch {
            isLoadingLiveData.value = true

            val response = withContext(Dispatchers.IO) {
                try {
                    services.getDetailAnimeCharacter(id)
                } catch (e: Throwable) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main){
                        isLoadingLiveData.value = false
                    }
                }
            }

            if (response is AnimeCharacterResponse) {
                isLoadingLiveData.value = false
                onSuccessCharacterLiveData.value = response.character
            } else {
                onFailLiveData.value = "Get Data Failed"
            }
        }
    }

    fun getDetailAnimeRecommendations(id: Int) {
        launch {
            isLoadingLiveData.value = true

            val response = withContext(Dispatchers.IO) {
                try {
                    services.getDetailAnimeRecommendations(id)
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