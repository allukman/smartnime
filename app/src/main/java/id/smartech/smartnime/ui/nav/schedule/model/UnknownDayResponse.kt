package id.smartech.smartnime.ui.nav.schedule.model

import com.google.gson.annotations.SerializedName

class UnknownDayResponse (
        @SerializedName("request_hash")val requestHash: String,
        @SerializedName("request_cached")val requestCached: Boolean,
        @SerializedName("request_cache_expiry")val requestCacheExpiry: Int,
        val unknown: ArrayList<DayModel>
)