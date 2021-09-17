package id.smartech.smartnime.ui.detail.manga.characters

import com.google.gson.annotations.SerializedName
import id.smartech.smartnime.ui.detail.anime.characters.AnimeCharacterModel

data class MangaCharactersResponse (
        @SerializedName("request_hash")val requestHash: String,
        @SerializedName("request_cached")val requestCached: Boolean,
        @SerializedName("request_cache_expiry")val requestCacheExpiry: Int,
        @SerializedName("characters")val character: ArrayList<MangaCharactersModel>
)