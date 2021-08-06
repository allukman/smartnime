package id.smartech.smartnime.ui.nav.schedule.model

import com.google.gson.annotations.SerializedName

data class GenresModel (
        @SerializedName("mal_id")val malId: Int,
        val type: String,
        val name: String,
        val url: String
)