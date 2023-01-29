package com.ryadamir.recipesapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * This is the model class for the Recipe item
 */

data class Recipe(
    @SerializedName("id")
    @Expose
    val id: String,
    @SerializedName("image")
    @Expose
    val imagePath: String,
    @SerializedName("thumb")
    @Expose
    val thumbPath: String,
    @SerializedName("name")
    @Expose
    val name: String,
    @SerializedName("description")
    @Expose
    val description: String,
    @SerializedName("calories")
    @Expose
    val calories: String,
    @SerializedName("carbos")
    @Expose
    val carbos: String,
    @SerializedName("fats")
    @Expose
    val fats: String,
    @SerializedName("proteins")
    @Expose
    val proteins: String,
    @SerializedName("time")
    @Expose
    val time: String
)
