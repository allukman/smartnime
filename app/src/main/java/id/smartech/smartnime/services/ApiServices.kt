package id.smartech.smartnime.services

import id.smartech.smartnime.model.TopAnimeResponse
import id.smartech.smartnime.model.TopMangaResponse
import id.smartech.smartnime.ui.nav.schedule.model.*
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiServices {

//    GET TOP Anime, Manga, Character, People
    @GET("top/anime/{page}/upcoming")
    suspend fun getTopAnime(@Path("page")page: Int): TopAnimeResponse

    @GET("top/manga/{page}")
    suspend fun getTopManga(@Path("page")page: Int): TopMangaResponse

//    GET Anime Schedule
    @GET("schedule/monday")
    suspend fun getMondaySchedule(): MondayResponse

    @GET("schedule/tuesday")
    suspend fun getTuesdaySchedule(): TuesdayResponse

    @GET("schedule/wednesday")
    suspend fun getWednesdaySchedule(): WednesdayResponse

    @GET("schedule/thursday")
    suspend fun getThursdaySchedule(): ThursdayResponse

    @GET("schedule/friday")
    suspend fun getFridaySchedule(): FridayResponse

    @GET("schedule/saturday")
    suspend fun getSaturdaySchedule(): SaturdayResponse

    @GET("schedule/sunday")
    suspend fun getSundaySchedule(): SundayResponse

    @GET("schedule/other")
    suspend fun getOtherSchedule(): OtherDayResponse

    @GET("schedule/unknown")
    suspend fun getUnknownSchedule(): UnknownDayResponse


}