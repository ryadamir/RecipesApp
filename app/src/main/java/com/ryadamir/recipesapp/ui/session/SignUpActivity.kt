package com.ryadamir.recipesapp.ui.session

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.ryadamir.recipesapp.source.local.DBHelper
import android.text.TextUtils
import android.widget.Toast
import android.content.Intent
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.*
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import android.view.animation.AnimationUtils
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.ryadamir.recipesapp.R
import com.ryadamir.recipesapp.databinding.ActivitySignupBinding
import com.ryadamir.recipesapp.ui.main.MainActivity
import com.ryadamir.recipesapp.util.SharedPrefManager
import com.ryadamir.recipesapp.viewmodels.SignUpViewModel
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * This is the sign up activity
 */

class SignUpActivity : AppCompatActivity() {

    private val binding: ActivitySignupBinding by lazy {
        ActivitySignupBinding.inflate(layoutInflater)
    }
    var dbHelper: DBHelper? = null
    var session: SharedPrefManager? = null

    private val signupViewModel: SignUpViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //Initializing local database and session manager
        initDatabaseShared()

        // setting the activity into full screen
        setFullScreen()

        // click listner for the register button
        signupAction()

    }

    private fun initDatabaseShared() {
        dbHelper = DBHelper(this)
        session = SharedPrefManager.getInstance(this)
    }

    private fun signupAction() {
        binding.signup.setOnClickListener {

            val user = binding.username.text.toString()
            val pass = binding.password.text.toString()
            val repass = binding.repassword.text.toString()

            if (TextUtils.isEmpty(user) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(repass))
                Toast.makeText(
                    this@SignUpActivity, getString(R.string.please_fill_fileds), Toast.LENGTH_LONG
                ).show()
            else {
                if (pass == repass) {
                    // disabling the click on the sign in button
                    binding.signup.isClickable = false

                    //checking if the user existes
                    val checkUser = signupViewModel.checkUsername(user, dbHelper!!)
                    if (!checkUser) {

                        //inserting the user in database
                        val insert = signupViewModel.insert(user, pass, dbHelper!!)

                        //checking if the insertion is done
                        if (insert) {
                            loginSuccess()

                        } else {
                            Toast.makeText(
                                this@SignUpActivity,
                                getString(R.string.registration_failed),
                                Toast.LENGTH_SHORT
                            ).show()

                            // enabling the click on the sign in button
                            binding.signup.isClickable = true

                            vibrate()
                        }
                    } else {
                        Toast.makeText(
                            this@SignUpActivity,
                            getString(R.string.user_exists),
                            Toast.LENGTH_SHORT
                        ).show()

                        // enabling the click on the sign in button
                        binding.signup.isClickable = true

                        vibrate()

                    }
                } else {
                    Toast.makeText(
                        this@SignUpActivity,
                        getString(R.string.passwords_not_matching),
                        Toast.LENGTH_SHORT
                    ).show()

                    // enabling the click on the sign in button
                    binding.signup.isClickable = true

                    vibrate()

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

            if (binding.check.drawable is AnimatedVectorDrawable) {
                val animatedVectorDrawable =
                    binding.check.drawable as AnimatedVectorDrawable
                animatedVectorDrawable.start()
            } else if (binding.check.drawable is AnimatedVectorDrawableCompat) {
                val animatedVectorDrawableCompat =
                    binding.check.drawable as AnimatedVectorDrawableCompat
                animatedVectorDrawableCompat.start()
            }

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
                signupViewModel.logUser(
                    username.text.toString(), password.text.toString(),
                    session!!
                )
                vibrate()

            }, 1500)

        }, 2000)
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