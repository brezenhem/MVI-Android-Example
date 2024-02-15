package com.mada.features.main

import android.content.Intent
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.airbnb.mvrx.viewModel
import com.mada.features.R
import com.mada.features.auth.main.AuthActivity
import com.mada.features.databinding.ActivityMainBinding
import com.mada.softpos.core.arch.MviActivity
import com.mada.softpos.core.arch.clearTaskAndStartActivity
import com.mada.softpos.core.arch.withEffect
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : MviActivity() {

    private lateinit var splashScreen: SplashScreen
    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainVM by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        initUi()
        initNav()
        checkUserSignedIn()

        setDecorFitsSystemWindows(false)
    }


    private fun initUi() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.setEvent(MainContract.Event.OnKeepOnScreenCondition(true))
    }

    private fun initNav() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        binding.navView.setupWithNavController(navHostFragment.navController)
    }

    private fun checkUserSignedIn() {
        viewModel.setEvent(MainContract.Event.OnCheckUserSignedIn(this))
    }

    override fun handleEffects() {
        withEffect(viewModel) { effect ->
            when (effect) {
                is MainContract.Effect.SetKeepOnScreenCondition -> setKeepOnScreenCondition(effect.value)
                is MainContract.Effect.StartIntent -> startLoginActivity(effect.intent)
                is MainContract.Effect.Logout -> clearTaskAndStartActivity(AuthActivity::class)
            }
        }
    }

    override fun invalidate() {}

    private fun setKeepOnScreenCondition(value: Boolean) {
        splashScreen.setKeepOnScreenCondition {
            value
        }
    }

    private fun startLoginActivity(intent: Intent) {
        startActivity(intent)
        finish()
    }
}
