package id.smartech.smartnime.ui.detail.manga.model

import com.google.gson.annotations.SerializedName
import id.smartech.smartnime.ui.detail.anime.model.AiredModel
import id.smartech.smartnime.ui.nav.schedule.model.GenresModel

data class DetailMangaResponse (
        @SerializedName("request_hash")val requestHash: String,
        @SerializedName("request_cached")val requestCached: Boolean,
        @SerializedName("request_cache_expiry")val requestCacheExpiry: Int,
        @SerializedName("mal_id")val malId: Int,
        val url: String,
        val title: String,
        @SerializedName("title_english")val titleEnglish: String?,
        @SerializedName("image_url")val imageUrl: String,
        val status: String?,
        val type: String,
        val volumes: Int?,
        val chapters: Int?,
        val score: Double?,
        val rank: Int?,
        val popularity: Int?,
        val members: Int?,
        val favorites: Int?,
        val synopsis: String?,
        val genres: ArrayList<GenresModel>,
        val published: AiredModel?
        )