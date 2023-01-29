package com.ryadamir.recipesapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.ryadamir.recipesapp.R
import com.ryadamir.recipesapp.listener.OnClickItemRecipe
import com.ryadamir.recipesapp.model.Recipe
import kotlinx.android.synthetic.main.list_recipe.view.*

/**
 * This is the Recipe adapter class
 */

class RecipeAdapter : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    private var list: MutableList<Recipe> = mutableListOf()
    var onClickListener: OnClickItemRecipe? = null

    inner class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(recipe: Recipe) {

            itemView.setOnClickListener {
                onClickListener?.onClick(recipe)
            }

            itemView.title_recipe.text = recipe.name

            //Applying regex on time value because it contains some letters (ex : PT35M)
            itemView.tv_time.text = recipe.time.replace("[^0-9.]".toRegex(),"") + " " + itemView.context.resources.getString(R.string.minutes)

            Glide.with(itemView).load(recipe.thumbPath)
                .transition(DrawableTransitionOptions.withCrossFade()).centerCrop()
                .into(itemView.iv_recipe)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.list_recipe, parent, false)
        return RecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setData(data: List<Recipe>) {
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }

}