package com.apollo.timeflow.ui

import android.os.Bundle
import android.view.Window
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.apollo.timeflow.databinding.ActivityMainBinding
import com.apollo.timeflow.lifecycleUtils.LifecycleUtils.quickStartCoroutineScope
import com.apollo.timeflow.viewmodel.MainViewModelProviderFactory
import com.apollo.timeflow.viewmodel.RouteViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null

    private lateinit var routeViewModel: RouteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        routeViewModel = ViewModelProvider(this, MainViewModelProviderFactory())[RouteViewModel::class.java]

        setupOnBackPressed()
        setupRouteNavigation()
    }

    private fun setupRouteNavigation() {
        fun navigation(navigationCode: Int) {
            supportFragmentManager.beginTransaction()
                .replace(
                    binding?.fragmentContainer?.id ?: return,
                    when (navigationCode) {
                        0 -> TimeFragment.newInstance()
                        else -> SettingFragment.newInstance()
                    },
                )
                .commit()
        }

        quickStartCoroutineScope(Dispatchers.Main) {
            routeViewModel.navigation.collectLatest {
                navigation(it)
            }
        }
    }

    private fun setupOnBackPressed() {
        val onBackPressedCallback = object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                this@MainActivity.onWindowFocusChanged(true)
                onBackPressedDispatcher.onBackPressed()
                onBackPressedDispatcher.dispatchOnBackCancelled()
            }
        }

        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        WindowCompat.getInsetsController(window, window.decorView).apply {
            if (hasFocus) {
                this.hide(WindowInsetsCompat.Type.statusBars())
                this.hide(WindowInsetsCompat.Type.navigationBars())
            }
        }
    }
}