package com.ryadamir.recipesapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemChangeListener
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.ryadamir.recipesapp.R
import com.ryadamir.recipesapp.ui.adapters.RecipeAdapter
import com.ryadamir.recipesapp.listener.OnClickItemRecipe
import com.ryadamir.recipesapp.model.Recipe
import com.ryadamir.recipesapp.ui.detail.DetailActivity
import com.ryadamir.recipesapp.viewmodels.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * This is the Home fragment class
 */

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by viewModel()

    private val adapterRecipe: RecipeAdapter by lazy {
        RecipeAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Instantiating the recycler view and the adapter
        setListRecipes()
        // adding the observer for the recipes list and adding it to the adapter
        observeRecipes()

        // adding the observer for the recipes list and adding it to the discover slider
        observeDiscover()

        // launching the data requesting from the viewmodel
        viewModel.requestDiscover()
        viewModel.requestRecipes()

        //set swipe to refresh listner
        setSwipeToRefresh()

    }

    private fun setSwipeToRefresh() {
        swipe_refresh.setOnRefreshListener {
            viewModel.requestDiscover()
            viewModel.requestRecipes()
            swipe_refresh.isRefreshing = false
        }
    }

    private fun observeDiscover() {
        viewModel.discoverResponseList.observe(viewLifecycleOwner) {
            // Loading recipes images to add in the slider
            loadImageList(it)
            // setting up the action on clicking the slider item
            navigationToDetailDiscover(it)
        }
    }

    private fun observeRecipes() {
        viewModel.recipesResponseList.observe(viewLifecycleOwner) {
            adapterRecipe.setData(it)
            progress.visibility = View.GONE
            recipes_title.visibility = View.VISIBLE
        }
    }

    private fun loadImageList(data: ArrayList<Recipe>) {

        val imageList = ArrayList<SlideModel>() // Create image list

        for (i in 5..10) {
            imageList.add(SlideModel("${data[i].imagePath}"))
        }

        image_slider.setImageList(imageList, ScaleTypes.CENTER_CROP)
        image_slider.setItemChangeListener(object : ItemChangeListener {
            override fun onItemChanged(position: Int) {
                txt_title_discover?.text = data[position].name
            }
        })
    }

    private fun navigationToDetailDiscover(recipe: ArrayList<Recipe>) {
        image_slider.setItemClickListener(object : ItemClickListener {
            override fun onItemSelected(position: Int) {
                navigationToDetail(recipe[position + 5])
            }
        })
    }

    private fun setListRecipes() {
        rv_recipes.setHasFixedSize(true)
        rv_recipes.adapter = adapterRecipe
        adapterRecipe.onClickListener = object : OnClickItemRecipe {
            override fun onClick(recipe: Recipe) {
                navigationToDetail(recipe)
            }
        }
    }

    private fun navigationToDetail(recipe: Recipe) {
        val intent = Intent(activity, DetailActivity::class.java)
        intent.putExtra("id", recipe.id)
        intent.putExtra("name", recipe.name)
        intent.putExtra("imagePath", recipe.imagePath)
        intent.putExtra("calories", recipe.calories)
        intent.putExtra("proteins", recipe.proteins)
        intent.putExtra("carbos", recipe.carbos)
        intent.putExtra("fats", recipe.fats)
        intent.putExtra("time", recipe.time)
        intent.putExtra("description", recipe.description)
        startActivity(intent)
    }

}