package id.smartech.smartnime.ui.nav.schedule

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import id.smartech.smartnime.model.TopAnimeModel
import id.smartech.smartnime.model.TopAnimeResponse
import id.smartech.smartnime.model.TopMangaModel
import id.smartech.smartnime.services.ApiServices
import id.smartech.smartnime.ui.nav.schedule.model.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class ScheduleViewModel: ViewModel(), CoroutineScope {
    private lateinit var services: ApiServices

    val onSuccessLiveData = MutableLiveData<List<DayModel>>()
    val onFailLiveData = MutableLiveData<String>()
    val isLoadingLiveData = MutableLiveData<Boolean>()

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setService(services: ApiServices) {
        this.services = services
    }

    fun getMondaySchedule() {
        launch {
            isLoadingLiveData.value = true

            val response = withContext(Dispatchers.IO) {
                try {
                    services.getMondaySchedule()
                } catch (e: Throwable) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main){
                        isLoadingLiveData.value = false
                    }
                }
            }

            if (response is MondayResponse) {
                isLoadingLiveData.value = false

                val list = response.monday.map {
                    DayModel(
                            malId = it.malId,
                            title = it.title,
                            imageUrl = it.imageUrl,
                            score = it.score,
                            genres = it.genres
                    )
                }
                onSuccessLiveData.value = list
            } else {
                onFailLiveData.value = "Get Data Failed"
            }
        }
    }

    fun getTuesdaySchedule() {
        launch {
            isLoadingLiveData.value = true

            val response = withContext(Dispatchers.IO) {
                try {
                    services.getTuesdaySchedule()
                } catch (e: Throwable) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main){
                        isLoadingLiveData.value = false
                    }
                }
            }

            if (response is TuesdayResponse) {
                isLoadingLiveData.value = false

                val list = response.tuesday.map {
                    DayModel(
                            malId = it.malId,
                            title = it.title,
                            imageUrl = it.imageUrl,
                            score = it.score,
                            genres = it.genres
                    )
                }
                onSuccessLiveData.value = list
            } else {
                onFailLiveData.value = "Get Data Failed"
            }
        }
    }

    fun getWednesdaySchedule() {
        launch {
            isLoadingLiveData.value = true

            val response = withContext(Dispatchers.IO) {
                try {
                    services.getWednesdaySchedule()
                } catch (e: Throwable) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main){
                        isLoadingLiveData.value = false
                    }
                }
            }

            if (response is WednesdayResponse) {
                isLoadingLiveData.value = false

                val list = response.wednesday.map {
                    DayModel(
                            malId = it.malId,
                            title = it.title,
                            imageUrl = it.imageUrl,
                            genres = it.genres,
                            score = it.score
                    )
                }
                onSuccessLiveData.value = list
            } else {
                onFailLiveData.value = "Get Data Failed"
            }
        }
    }

    fun getThursdaySchedule() {
        launch {
            isLoadingLiveData.value = true

            val response = withContext(Dispatchers.IO) {
                try {
                    services.getThursdaySchedule()
                } catch (e: Throwable) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main){
                        isLoadingLiveData.value = false
                    }
                }
            }

            if (response is ThursdayResponse) {
                isLoadingLiveData.value = false

                val list = response.thursday.map {
                    DayModel(
                            malId = it.malId,
                            title = it.title,
                            imageUrl = it.imageUrl,
                            score = it.score,
                            genres = it.genres
                    )
                }
                onSuccessLiveData.value = list
            } else {
                onFailLiveData.value = "Get Data Failed"
            }
        }
    }

    fun getFridaySchedule() {
        launch {
            isLoadingLiveData.value = true

            val response = withContext(Dispatchers.IO) {
                try {
                    services.getFridaySchedule()
                } catch (e: Throwable) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main){
                        isLoadingLiveData.value = false
                    }
                }
            }

            if (response is FridayResponse) {
                isLoadingLiveData.value = false

                val list = response.friday.map {
                    DayModel(
                            malId = it.malId,
                            title = it.title,
                            imageUrl = it.imageUrl,
                            score = it.score,
                            genres = it.genres
                    )
                }
                onSuccessLiveData.value = list
            } else {
                onFailLiveData.value = "Get Data Failed"
            }
        }
    }

    fun getSaturdaySchedule() {
        launch {
            isLoadingLiveData.value = true

            val response = withContext(Dispatchers.IO) {
                try {
                    services.getSaturdaySchedule()
                } catch (e: Throwable) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main){
                        isLoadingLiveData.value = false
                    }
                }
            }

            if (response is SaturdayResponse) {
                isLoadingLiveData.value = false

                val list = response.saturday.map {
                    DayModel(
                            malId = it.malId,
                            title = it.title,
                            imageUrl = it.imageUrl,
                            genres = it.genres,
                            score = it.score
                    )
                }
                onSuccessLiveData.value = list
            } else {
                onFailLiveData.value = "Get Data Failed"
            }
        }
    }

    fun getSundaySchedule() {
        launch {
            isLoadingLiveData.value = true

            val response = withContext(Dispatchers.IO) {
                try {
                    services.getSundaySchedule()
                } catch (e: Throwable) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main){
                        isLoadingLiveData.value = false
                    }
                }
            }

            if (response is SundayResponse) {
                isLoadingLiveData.value = false

                val list = response.sunday.map {
                    DayModel(
                            malId = it.malId,
                            title = it.title,
                            imageUrl = it.imageUrl,
                            score = it.score,
                            genres = it.genres
                    )
                }
                onSuccessLiveData.value = list
            } else {
                onFailLiveData.value = "Get Data Failed"
            }
        }
    }

    fun getOtherDaySchedule() {
        launch {
            isLoadingLiveData.value = true

            val response = withContext(Dispatchers.IO) {
                try {
                    services.getOtherSchedule()
                } catch (e: Throwable) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main){
                        isLoadingLiveData.value = false
                    }
                }
            }

            if (response is OtherDayResponse) {
                isLoadingLiveData.value = false

                val list = response.other.map {
                    DayModel(
                            malId = it.malId,
                            title = it.title,
                            imageUrl = it.imageUrl,
                            score = it.score,
                            genres = it.genres
                    )
                }
                onSuccessLiveData.value = list
            } else {
                onFailLiveData.value = "Get Data Failed"
            }
        }
    }

    fun getUnknownDaySchedule() {
        launch {
            isLoadingLiveData.value = true

            val response = withContext(Dispatchers.IO) {
                try {
                    services.getUnknownSchedule()
                } catch (e: Throwable) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main){
                        isLoadingLiveData.value = false
                    }
                }
            }

            if (response is UnknownDayResponse) {
                isLoadingLiveData.value = false

                val list = response.unknown.map {
                    DayModel(
                            malId = it.malId,
                            title = it.title,
                            imageUrl = it.imageUrl,
                            score = it.score,
                            genres = it.genres
                    )
                }
                onSuccessLiveData.value = list
            } else {
                onFailLiveData.value = "Get Data Failed"
            }
        }
    }
}