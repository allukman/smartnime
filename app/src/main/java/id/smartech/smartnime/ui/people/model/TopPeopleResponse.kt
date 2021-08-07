package id.smartech.smartnime.ui.people.model

import com.google.gson.annotations.SerializedName
import id.smartech.smartnime.model.TopAnimeModel

data class TopPeopleResponse (
    @SerializedName("request_hash")val requestHash: String,
    @SerializedName("request_cached")val requestCached: Boolean,
    @SerializedName("request_cache_expiry")val requestCacheExpiry: Int,
    val top: ArrayList<TopPeopleModel>
)