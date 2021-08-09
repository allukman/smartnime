package id.smartech.smartnime.ui.nav.characters

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.smartech.smartnime.services.ApiServices
import id.smartech.smartnime.ui.nav.people.model.TopPeopleModel
import id.smartech.smartnime.ui.nav.people.model.TopPeopleResponse
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class TopCharactersViewModel: ViewModel(), CoroutineScope {
    private lateinit var services: ApiServices

    val onSuccessLiveData = MutableLiveData<List<TopPeopleModel>>()
    val onFailLiveData = MutableLiveData<String>()
    val isLoadingLiveData = MutableLiveData<Boolean>()

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setService(services: ApiServices) {
        this.services = services
    }

    fun getTopCharacters() {
        launch {
            isLoadingLiveData.value = true

            val response = withContext(Dispatchers.IO) {
                try {
                    services.getTopCharacters(1)
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