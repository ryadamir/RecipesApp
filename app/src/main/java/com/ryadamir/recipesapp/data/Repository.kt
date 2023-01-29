package com.ryadamir.recipesapp.data

import com.ryadamir.recipesapp.model.Recipe
import com.ryadamir.recipesapp.source.remote.Api
import io.reactivex.Observable

/**
 * This is the repository class for the retrofit API
 */

class Repository(private val api: Api) {

    fun getDiscover(): Observable<ArrayList<Recipe>> {
        return api.getRecipes()
    }

    fun getRecipes(): Observable<ArrayList<Recipe>> {
        return api.getRecipes()
    }

}