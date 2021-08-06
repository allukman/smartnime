package id.smartech.smartnime.services

import id.smartech.smartnime.model.TopAnimeResponse
import id.smartech.smartnime.model.TopMangaResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiServices {

//    GET TOP Anime, Manga, Character, People
    @GET("top/anime/{page}/upcoming")
    suspend fun getTopAnime(@Path("page")page: Int): TopAnimeResponse

    @GET("top/manga/{page}")
    suspend fun getTopManga(@Path("page")page: Int): TopMangaResponse


}