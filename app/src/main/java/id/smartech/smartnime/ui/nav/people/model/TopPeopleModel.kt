package id.smartech.smartnime.ui.nav.people.model

import com.google.gson.annotations.SerializedName

data class TopPeopleModel (
    @SerializedName("mal_id")val malId: Int,
    val title: String,
    val url: String,
    @SerializedName("name_kanji")val nameKanji: String,
    val favorites: Int,
    @SerializedName("image_url")val imageUrl: String
)