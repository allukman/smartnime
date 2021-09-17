package id.smartech.smartnime.ui.detail.anime.model

import com.google.gson.annotations.SerializedName
import id.smartech.smartnime.ui.nav.schedule.model.GenresModel

data class DetailAnimeModel (
        @SerializedName("request_hash")val requestHash: String,
        @SerializedName("request_cached")val requestCached: Boolean,
        @SerializedName("request_cache_expiry")val requestCacheExpiry: Int,
        @SerializedName("mal_id")val malId: Int,
        val url: String,
        @SerializedName("image_url")val imageUrl: String,
        @SerializedName("trailer_url")val trailerUrl: String?,
        val title: String,
        @SerializedName("title_english")val titleEnglish: String?,
        val source: String?,
        val type: String,
        val episodes: Int?,
        val status: String?,
        val duration: String?,
        val rating: String?,
        val score: Double?,
        val rank: Int?,
        val popularity: Int?,
        val members: Int?,
        val favorites: Int?,
        val synopsis: String?,
        val premiered: String?,
        val genres: ArrayList<GenresModel>,
        val licensors: ArrayList<StudioModel>?,
        val studios: ArrayList<StudioModel>?,
        val aired: AiredModel?,
        @SerializedName("opening_themes") val openingThemes : List<String>,
        @SerializedName("ending_themes") val endingThemes : List<String>

)