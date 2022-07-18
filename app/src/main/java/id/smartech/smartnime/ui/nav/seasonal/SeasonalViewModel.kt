package id.smartech.smartnime.ui.nav.seasonal

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import id.smartech.smartnime.services.ApiServices
import id.smartech.smartnime.ui.nav.schedule.model.DayModel
import id.smartech.smartnime.ui.nav.schedule.model.MondayResponse
import id.smartech.smartnime.ui.nav.seasonal.model.SeasonalAnimeModel
import id.smartech.smartnime.ui.nav.seasonal.model.SeasonalAnimeResponse
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class SeasonalViewModel: ViewModel(), CoroutineScope {
    private lateinit var services: ApiServices

    val onSuccessLiveData = MutableLiveData<List<SeasonalAnimeModel>>()
    val onFailLiveData = MutableLiveData<String>()
    val isLoadingLiveData = MutableLiveData<Boolean>()

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setService(services: ApiServices) {
        this.services = services
    }

    fun getAnimeSeasonal(year: String, season: String) {
        launch {
            isLoadingLiveData.value = true

            val response = withContext(Dispatchers.IO) {
                try {
                    services.getAnimeSeasonal(year, season)
                } catch (e: Throwable) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main){
                        isLoadingLiveData.value = false
                    }
                }
            }

            if (response is SeasonalAnimeResponse) {
                isLoadingLiveData.value = false

                val list = response.anime?.map {
                    SeasonalAnimeModel(
                            malId = it.malId,
                            title = it.title,
                            imageUrl = it.imageUrl,
                            genres = it.genres,
                            members = it.members,
                            type = it.type
                    )
                }
                Log.d("anu", Gson().toJson(response.anime))
                onSuccessLiveData.value = list
            } else {
                onFailLiveData.value = "Get Data Failed"
            }
        }
    }
}