package id.smartech.smartnime.ui.detail.anime.characters

import com.google.gson.annotations.SerializedName
import id.smartech.smartnime.ui.detail.anime.model.VoiceActorModel

data class AnimeCharacterModel (
        @SerializedName("mal_id")val malId: Int,
        @SerializedName("url")val url: String?,
        @SerializedName("image_url")val imageUrl: String?,
        @SerializedName("name")val name: String?,
        @SerializedName("voice_actors")val voiceActors: ArrayList<VoiceActorModel>
)