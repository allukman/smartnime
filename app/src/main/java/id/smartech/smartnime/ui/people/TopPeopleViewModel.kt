package id.smartech.smartnime.ui.people

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.smartech.smartnime.model.TopAnimeModel
import id.smartech.smartnime.model.TopAnimeResponse
import id.smartech.smartnime.model.TopMangaModel
import id.smartech.smartnime.services.ApiServices
import id.smartech.smartnime.ui.people.model.TopPeopleModel
import id.smartech.smartnime.ui.people.model.TopPeopleResponse
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class TopPeopleViewModel: ViewModel(), CoroutineScope {
    private lateinit var services: ApiServices

    val onSuccessLiveData = MutableLiveData<List<TopPeopleModel>>()
    val onFailLiveData = MutableLiveData<String>()
    val isLoadingLiveData = MutableLiveData<Boolean>()

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setService(services: ApiServices) {
        this.services = services
    }

    fun getTopAnime() {
        launch {
            isLoadingLiveData.value = true

            val response = withContext(Dispatchers.IO) {
                try {
                    services.getTopPeople(1)
                } catch (e: Throwable) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main){
                        isLoadingLiveData.value = false
                    }
                }
            }

            if (response is TopPeopleResponse) {
                isLoadingLiveData.value = false

                val list = response.top.map {
                    TopPeopleModel(
                        malId = it.malId,
                        imageUrl = it.imageUrl,
                        title = it.title,
                        url = it.url,
                        birthday = it.url,
                        favorites = it.favorites,
                        nameKanji = it.nameKanji
                    )
                }
                onSuccessLiveData.value = list
            } else {
                onFailLiveData.value = "Get Data Failed"
            }
        }
    }
}