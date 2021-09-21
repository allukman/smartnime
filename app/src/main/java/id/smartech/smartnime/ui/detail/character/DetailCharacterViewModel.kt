package id.smartech.smartnime.ui.detail.character

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.smartech.smartnime.services.ApiServices
import id.smartech.smartnime.ui.detail.anime.model.DetailAnimeModel
import id.smartech.smartnime.ui.detail.character.model.AnimeographyModel
import id.smartech.smartnime.ui.detail.character.model.DetailCharacterResponse
import id.smartech.smartnime.ui.detail.character.model.VoiceActorModel
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class DetailCharacterViewModel: ViewModel(), CoroutineScope {
    private lateinit var services: ApiServices

    val onSuccessLiveData = MutableLiveData<DetailCharacterResponse>()
    val onSuccessAnimeographyLiveData = MutableLiveData<List<AnimeographyModel>>()
    val onSuccessMangaographyLiveData = MutableLiveData<List<AnimeographyModel>>()
    val onSuccessVoiceActorLiveData = MutableLiveData<List<VoiceActorModel>>()
    val onFailLiveData = MutableLiveData<String>()
    val isLoadingLiveData = MutableLiveData<Boolean>()

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setService(services: ApiServices) {
        this.services = services
    }

    fun getDetailCharacter(id: Int) {
        launch {
            isLoadingLiveData.value = true

            val response = withContext(Dispatchers.IO) {
                try {
                    services.getDetailCharacter(id)
                } catch (e: Throwable) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main){
                        isLoadingLiveData.value = false
                    }
                }
            }

            if (response is DetailCharacterResponse) {
                isLoadingLiveData.value = false
                onSuccessLiveData.value = response

                val listAnimeography = response.animeography?.map {
                    AnimeographyModel(
                            malId = it.malId,
                            role = it.role,
                            name = it.name,
                            url = it.url,
                            imageUrl = it.imageUrl
                    )
                }

                val listMangaography = response.mangaography?.map {
                    AnimeographyModel(
                            malId = it.malId,
                            role = it.role,
                            name = it.name,
                            url = it.url,
                            imageUrl = it.imageUrl
                    )
                }

                val listVoiceActor = response.voiceActor?.map {
                    VoiceActorModel(
                            malId = it.malId,
                            imageUrl = it.imageUrl,
                            url = it.url,
                            name = it.name,
                            language = it.language
                    )
                }

                onSuccessAnimeographyLiveData.value = listAnimeography
                onSuccessMangaographyLiveData.value = listMangaography
                onSuccessVoiceActorLiveData.value = listVoiceActor

            } else {
                onFailLiveData.value = "Get Data Failed"
            }
        }
    }
}