package id.smartech.smartnime.ui.detail.anime.characters

import com.google.gson.annotations.SerializedName

data class AnimeCharacterResponse (
        @SerializedName("request_hash")val requestHash: String,
        @SerializedName("request_cached")val requestCached: Boolean,
        @SerializedName("request_cache_expiry")val requestCacheExpiry: Int,
        @SerializedName("characters")val character: ArrayList<AnimeCharacterModel>
)