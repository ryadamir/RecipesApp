package com.ryadamir.recipesapp.ui.session

import android.content.Context
import android.content.Intent
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.*
import android.text.TextUtils
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.ryadamir.recipesapp.R
import com.ryadamir.recipesapp.databinding.ActivityLoginBinding
import com.ryadamir.recipesapp.ui.main.MainActivity
import com.ryadamir.recipesapp.source.local.DBHelper
import com.ryadamir.recipesapp.util.SharedPrefManager
import com.ryadamir.recipesapp.viewmodels.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * This is the login activity
 */

class LoginActivity : AppCompatActivity() {

    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    var dbHelper: DBHelper? = null
    var session: SharedPrefManager? = null

    private val loginViewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //Initializing local database and session manager
        initDatabaseShared()

        // setting the activity into full screen
        setFullScreen()

        // click listner for the login button
        loginAction()

        // click listner to go to the register activity
        registerAction()

        // Check if user is logged in
        isUserLoggedIn()

    }

    private fun initDatabaseShared() {
        dbHelper = DBHelper(this)
        session = SharedPrefManager.getInstance(this)
    }

    private fun registerAction() {
        binding.signup.setOnClickListener {
            val intent = Intent(applicationContext, SignUpActivity::class.java)
            startActivity(intent)
        }

    }

    private fun loginAction() {
        binding.signin.setOnClickListener {

            val user = binding.username.text.toString()
            val pass = binding.password.text.toString()

            if (TextUtils.isEmpty(user) || TextUtils.isEmpty(pass))
            // Fill all fields message
                Toast.makeText(
                    this@LoginActivity, getString(R.string.please_fill_fileds), Toast.LENGTH_SHORT
                ).show()
            else {
                // disabling the click on the sign in button
                binding.signin.isClickable = false

                if (loginViewModel.login(user, pass, dbHelper!!)) {
                    loginSuccess()

                } else {
                    vibrate()
                    // Failed to login message
                    Toast.makeText(
                        this@LoginActivity,
                        getString(R.string.login_failed),
                        Toast.LENGTH_SHORT
                    ).show()

                    // enabling the click on the sign in button
                    binding.signin.isClickable = true

                }
            }
        }

    }

    private fun loginSuccess() {
        // Animating the login view
        binding.arrow.visibility = View.GONE
        binding.progress.visibility = View.VISIBLE

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            binding.progress.visibility = View.GONE
            binding.check.visibility = View.VISIBLE

            // animating the check icon
            if (binding.check.drawable is AnimatedVectorDrawable) {
                val animatedVectorDrawable =
                    binding.check.drawable as AnimatedVectorDrawable
                animatedVectorDrawable.start()
            } else if (binding.check.drawable is AnimatedVectorDrawableCompat) {
                val animatedVectorDrawableCompat =
                    binding.check.drawable as AnimatedVectorDrawableCompat
                animatedVectorDrawableCompat.start()
            }

            // animating the welcome text
            binding.bienvenueText.animation = AnimationUtils.loadAnimation(
                applicationContext, R.anim.slide_out_left
            )
            val handler = Handler(Looper.getMainLooper())
            handler.postDelayed({
                binding.bienvenueText.text =
                    resources.getString(R.string.welcome)
                binding.bienvenueText.animation = AnimationUtils.loadAnimation(
                    applicationContext, R.anim.left_to_right
                )

            }, 400)

            val handler2 = Handler(Looper.getMainLooper())
            handler2.postDelayed({
                // Launching main activity
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                finishAffinity()

                // adding the user to the shared preferences
                loginViewModel.logUser(
                    username.text.toString(), password.text.toString(),
                    session!!
                )

                vibrate()

            }, 1500)

        }, 2000)
    }

    private fun isUserLoggedIn() {
        if (loginViewModel.isUserLoggedIn(session!!)) {
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }
    }

    private fun vibrate() {
        val vib = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager =
                getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            getSystemService(VIBRATOR_SERVICE) as Vibrator
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vib.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            @Suppress("DEPRECATION")
            vib.vibrate(100)
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