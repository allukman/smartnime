package id.smartech.smartnime.ui.detail.anime.episodes.model

import com.google.gson.annotations.SerializedName
import id.smartech.smartnime.ui.detail.anime.episodes.model.EpisodesModel

data class EpisodesResponse (
    @SerializedName("request_hash")val requestHash: String,
    @SerializedName("request_cached")val requestCached: Boolean,
    @SerializedName("request_cache_expiry")val requestCacheExpiry: Int,
    @SerializedName("episodes")val episodes: ArrayList<EpisodesModel>
)