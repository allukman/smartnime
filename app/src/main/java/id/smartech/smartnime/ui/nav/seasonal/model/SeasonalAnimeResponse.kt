package id.smartech.smartnime.ui.nav.seasonal.model

import com.google.gson.annotations.SerializedName
import id.smartech.smartnime.ui.nav.people.model.TopPeopleModel

data class SeasonalAnimeResponse (
        @SerializedName("request_hash")val requestHash: String,
        @SerializedName("request_cached")val requestCached: Boolean,
        @SerializedName("request_cache_expiry")val requestCacheExpiry: Int,
        @SerializedName("season_name")val seasonName: String,
        @SerializedName("2020")val seasonYear: String,
        val anime: ArrayList<SeasonalAnimeModel>?
)