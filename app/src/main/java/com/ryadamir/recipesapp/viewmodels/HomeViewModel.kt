package com.ryadamir.recipesapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ryadamir.recipesapp.data.Repository
import com.ryadamir.recipesapp.model.Recipe
import io.reactivex.disposables.CompositeDisposable

/**
 * This is the viewmodel for the Home fragment
 */

class HomeViewModel(private val repository: Repository) : ViewModel() {

    private val _discoverResponseList = MutableLiveData<ArrayList<Recipe>>()
    val discoverResponseList: LiveData<ArrayList<Recipe>> = _discoverResponseList

    private val _recipesResponseList = MutableLiveData<ArrayList<Recipe>>()
    val recipesResponseList: LiveData<ArrayList<Recipe>> = _recipesResponseList

    private val _errorMessage = MutableLiveData<String>()

    private val compositeDisposable by lazy {
        CompositeDisposable()
    }

    fun requestDiscover() {
        val discoverDisposable = repository.getDiscover().doOnSubscribe { }.doFinally { }.subscribe(
                { _discoverResponseList.postValue(it) },
                { _errorMessage.postValue(it.localizedMessage) })
        compositeDisposable.add(discoverDisposable)
    }

    fun requestRecipes() {
        val recipesDisposable = repository.getRecipes().doOnSubscribe { }.doFinally { }
            .subscribe({ _recipesResponseList.postValue(it) },
                { _errorMessage.postValue(it.localizedMessage) })
        compositeDisposable.add(recipesDisposable)
    }

}