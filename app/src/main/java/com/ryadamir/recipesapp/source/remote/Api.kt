package com.ryadamir.recipesapp.source.remote

import com.ryadamir.recipesapp.model.Recipe
import io.reactivex.Observable
import retrofit2.http.GET

/**
 * This is the retrofit client interface
 */

interface Api {

    @GET("recipes.json")
    fun getRecipes(): Observable<ArrayList<Recipe>>

}
