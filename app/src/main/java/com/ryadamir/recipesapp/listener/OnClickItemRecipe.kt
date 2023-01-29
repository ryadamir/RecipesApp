package com.ryadamir.recipesapp.listener

import com.ryadamir.recipesapp.model.Recipe

/**
 * This is the click listner for the Recipe adapter
 */

interface OnClickItemRecipe {
    fun onClick(recipe: Recipe)
}