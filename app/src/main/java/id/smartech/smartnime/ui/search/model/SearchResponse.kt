package id.smartech.smartnime.ui.search.model

import com.google.gson.annotations.SerializedName

data class SearchResponse (
        @SerializedName("request_hash")val requestHash: String,
        @SerializedName("request_cached")val requestCached: Boolean,
        @SerializedName("request_cache_expiry")val requestCacheExpiry: Int,
        val results: ArrayList<SearchModel>
)
