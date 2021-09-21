package id.smartech.smartnime.services

import id.smartech.smartnime.model.RecommendationsResponse
import id.smartech.smartnime.model.TopAnimeResponse
import id.smartech.smartnime.model.TopMangaResponse
import id.smartech.smartnime.ui.detail.anime.characters.AnimeCharacterResponse
import id.smartech.smartnime.ui.detail.anime.model.DetailAnimeModel
import id.smartech.smartnime.ui.detail.anime.episodes.model.EpisodesResponse
import id.smartech.smartnime.ui.detail.character.model.DetailCharacterResponse
import id.smartech.smartnime.ui.detail.manga.characters.MangaCharactersResponse
import id.smartech.smartnime.ui.detail.manga.model.DetailMangaResponse
import id.smartech.smartnime.ui.nav.schedule.model.*
import id.smartech.smartnime.ui.nav.people.model.TopPeopleResponse
import id.smartech.smartnime.ui.nav.seasonal.model.SeasonalAnimeResponse
import id.smartech.smartnime.ui.search.model.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

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

//    GET Detail Anime
    @GET("anime/{id}")
    suspend fun getDetailAnime(@Path("id")id: Int) : DetailAnimeModel

    @GET("anime/{id}/characters_staff")
    suspend fun getDetailAnimeCharacter(@Path("id")id: Int) : AnimeCharacterResponse

    @GET("anime/{id}/episodes")
    suspend fun getDetailAnimeEpisodes(@Path("id")id: Int) : EpisodesResponse

    @GET("anime/{id}/recommendations")
    suspend fun getDetailAnimeRecommendations(@Path("id")id: Int) : RecommendationsResponse

//    GET Detail Manga
    @GET("manga/{id}")
    suspend fun getDetailManga(@Path("id")id: Int) : DetailMangaResponse

    @GET("manga/{id}/characters")
    suspend fun getDetailMangaCharacters(@Path("id")id: Int) : MangaCharactersResponse

    @GET("manga/{id}/recommendations")
    suspend fun getDetailMangaRecommendations(@Path("id")id: Int) : RecommendationsResponse

//    GET Detail Character
    @GET("character/{id}")
    suspend fun getDetailCharacter(@Path("id")id: Int): DetailCharacterResponse

//    SEARCH
    @GET("search/{type}?")
    suspend fun getSearch(@Path("type")type: String, @Query("q")query: String) : SearchResponse

}