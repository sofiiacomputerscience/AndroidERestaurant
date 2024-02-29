package fr.isen.boldeskul.androiderestaurant.network

import com.google.gson.annotations.SerializedName
import java.io.Serializable

//data class Category(@SerializedName ("name_fr") val name : String,
//                    val items : List<Dish>) : Serializable

data class Category(
    @SerializedName("name_fr") val name: String,
    @SerializedName("items") val items: List<Dish>
): Serializable