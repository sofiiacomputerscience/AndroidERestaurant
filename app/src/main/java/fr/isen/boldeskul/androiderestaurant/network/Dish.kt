package fr.isen.boldeskul.androiderestaurant.network

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Dish(@SerializedName ("name_fr") val name : String,
                val images : List<String>,
                val ingredients: List<Ingredient>,
                val prices: List<Price>) : Serializable