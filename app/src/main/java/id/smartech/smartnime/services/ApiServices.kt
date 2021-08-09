package id.smartech.smartnime.services

import id.smartech.smartnime.model.TopAnimeResponse
import id.smartech.smartnime.model.TopMangaResponse
import id.smartech.smartnime.ui.nav.schedule.model.*
import id.smartech.smartnime.ui.nav.people.model.TopPeopleResponse
import id.smartech.smartnime.ui.nav.seasonal.model.SeasonalAnimeResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiServices {

//    GET TOP Anime, Manga, Character, People
    @GET("top/anime/{page}/upcoming")
    suspend fun getTopAnime(@Path("page")page: Int): TopAnimeResponse

    @GET("top/manga/{page}")
    suspend fun getTopManga(@Path("page")page: Int): TopMangaResponse

    @GET("top/people/{page}")
    suspend fun getTopPeople(@Path("page")page: Int): TopPeopleResponse

    @GET("top/characters/{page}")
    suspend fun getTopCharacters(@Path("page")page: Int): TopPeopleResponse

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

//    GET Anime Seasonal
    @GET("season/{year}/{season}")
    suspend fun getAnimeSeasonal(@Path("year")year: String, @Path("season")season: String): SeasonalAnimeResponse

//    GET Anime
    @GET("top/anime/1/upcoming")
    suspend fun getUpcomingAnime(): TopAnimeResponse

    @GET("top/anime/1/airing")
    suspend fun getAiringAnime(): TopAnimeResponse

    @GET("top/anime/1/bypopularity")
    suspend fun getPopularAnime(): TopAnimeResponse

    @GET("top/anime/1/favorite")
    suspend fun getFavoriteAnime(): TopAnimeResponse

    @GET("top/anime/1")
    suspend fun getTopAnime(): TopAnimeResponse

//    GET Manga
    @GET("top/manga/1/{category}")
    suspend fun getMangaCategory(@Path("category")category: String): TopMangaResponse

    @GET("top/manga/1/bypopularity")
    suspend fun getPopularManga(): TopMangaResponse

    @GET("top/manga/1/favorite")
    suspend fun getFavoriteManga(): TopMangaResponse



}