package id.smartech.smartnime.ui.search

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.smartech.smartnime.services.ApiServices
import id.smartech.smartnime.ui.search.model.SearchModel
import id.smartech.smartnime.ui.search.model.SearchResponse
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class SearchViewModel: ViewModel(), CoroutineScope {
    private lateinit var services: ApiServices

    val onSuccessLiveData = MutableLiveData<List<SearchModel>>()
    val onFailLiveData = MutableLiveData<Boolean>()
    val isLoadingLiveData = MutableLiveData<Boolean>()

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setService(services: ApiServices) {
        this.services = services
    }

    fun getSearch(filter: String, query: String) {
        launch {
            isLoadingLiveData.value = true

            val response = withContext(Dispatchers.IO) {
                try {
                    services.getSearch(filter, query)
                } catch (e: Throwable) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main){
                        isLoadingLiveData.value = false
                        onFailLiveData.value = true
                    }
                }
            }

            if (response is SearchResponse) {
                isLoadingLiveData.value = false
                onFailLiveData.value = false

                val list = response.results?.map {
                    SearchModel(
                            malId = it.malId,
                            imageUrl = it.imageUrl,
                            title = it.title,
                            url = it.url,
                            name = it.name
                    )
                }
                onSuccessLiveData.value = list
            } else {
                onFailLiveData.value = true
            }
        }
    }
}