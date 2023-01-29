package com.ryadamir.recipesapp.ui.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.ryadamir.recipesapp.R
import com.ryadamir.recipesapp.databinding.ActivityDetailBinding
import com.ryadamir.recipesapp.model.Recipe
import kotlinx.android.synthetic.main.activity_detail.*


/**
 * This is the details activity for the Recipe
 */

class DetailActivity : AppCompatActivity() {

    private val binding: ActivityDetailBinding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // setting the activity to full screen
        setFullScreen()
        //getting data and loading it
        loadDetail()
        // share the recipe action
        shareAction()
        // go back action
        backAction()
    }

    @SuppressLint("SetTextI18n")
    private fun loadDetail() {
        // Loading the image
        loadPoster()

        //Getting informations from intent and setting them
        val id: String = intent.getStringExtra("id")!!
        val name: String = intent.getStringExtra("name")!!
        val calories: String = intent.getStringExtra("calories")!!
        val proteins: String = intent.getStringExtra("proteins")!!
        val carbos: String = intent.getStringExtra("carbos")!!
        val fats: String = intent.getStringExtra("fats")!!
        val time: String = intent.getStringExtra("time")!!
        val description: String = intent.getStringExtra("description")!!

        binding.txtTitleDetail.text = name
        binding.txtDescriptionDetail.text = description

        //Applying regex on time value because it contains some letters (ex : PT35M)
        binding.txtTimeDetail.text =
            resources.getString(R.string.time) + time.replace(
                "[^0-9.]".toRegex(),
                ""
            ) + " " + applicationContext.resources.getString(R.string.minutes)

        binding.txtCaloriesDetail.text =
            resources.getString(R.string.calories) + calories

        binding.txtProteinsDetail.text =
            resources.getString(R.string.proteins) + proteins

        binding.txtCarbosDetail.text =
            resources.getString(R.string.carbos) + carbos

        binding.txtFatsDetail.text =
            resources.getString(R.string.fats) + fats

        binding.infosContainer.visibility = View.VISIBLE

        binding.progress.visibility = View.GONE

    }

    private fun loadPoster() {
        // Getting the image url value then giving it to the Glide component to load it
        val imagePath: String = intent.getStringExtra("imagePath")!!

        Glide.with(this)
            .load("$imagePath")
            .transition(DrawableTransitionOptions.withCrossFade())
            .centerCrop()
            .into(binding.ivPoster)
    }

    private fun shareAction() {
        // To share the recipe on ther apps
        binding.btnShare.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, intent.getStringExtra("name")!!)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
    }

    private fun backAction() {
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun setFullScreen() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            window.attributes.layoutInDisplayCutoutMode =
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
            window.insetsController?.apply {
                hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_IMMERSIVE
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)
    }

}