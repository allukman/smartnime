package id.smartech.smartnime.ui.nav.schedule.model

import com.google.gson.annotations.SerializedName

data class DayModel(
        @SerializedName("mal_id")val malId: Int,
        @SerializedName("title") val title: String,
        @SerializedName("image_url")val imageUrl: String,
        @SerializedName("score") val score: Double?,
        val genres: ArrayList<GenresModel>
)