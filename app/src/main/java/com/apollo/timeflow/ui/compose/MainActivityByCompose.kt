package com.apollo.timeflow.ui.compose

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.apollo.timeflow.boardcast.DateBroadcast
import com.apollo.timeflow.boardcast.TimeBroadcast
import com.apollo.timeflow.getDeviceType
import com.apollo.timeflow.ui.compose.screenAdaptation.Card
import com.apollo.timeflow.viewmodel.MainViewModel
import com.apollo.timeflow.viewmodel.MainViewModelProviderFactory

class MainActivityByCompose : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var timeChangeReceiver: TimeBroadcast
    private lateinit var dateChangeReceiver: DateBroadcast

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        mainViewModel = ViewModelProvider(
            this, MainViewModelProviderFactory()
        )[MainViewModel::class.java]

        this.addBroadcast()

        val mContext = applicationContext ?: return
        val view = ComposeView(mContext)

        view.setContent {
            ComposeUI()
        }
        setContentView(view)

        mainViewModel.updateDate()
    }

    @Preview
    @Composable
    private fun ComposeUI() {
        Scaffold { padding ->
            padding.hashCode()

            val isShowTimeFormatState =
                mainViewModel.timeFormatRecordDataStoreFlow.collectAsState(initial = false)
            val isShowDateFormatState =
                mainViewModel.isDateShowDataStoreFlow.collectAsState(initial = false)

            Card(
                deviceTypes = getDeviceType(),
                leftOnClick = {
                    mainViewModel.timeFormatRecordUpdate(!isShowTimeFormatState.value)
                },
                rightOnClick = {
                    mainViewModel.isDateShow(!isShowDateFormatState.value)
                },
            )
        }
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

    private fun addBroadcast() {
        val intentFilterTimeChange = IntentFilter()
        intentFilterTimeChange.addAction(Intent.ACTION_TIME_TICK)
        intentFilterTimeChange.addAction(Intent.ACTION_TIME_CHANGED)
        intentFilterTimeChange.addAction(Intent.ACTION_TIMEZONE_CHANGED)
        intentFilterTimeChange.addAction(Intent.ACTION_LOCALE_CHANGED)

        timeChangeReceiver = TimeBroadcast {
            mainViewModel.updateTime()
        }

        ContextCompat.registerReceiver(
            applicationContext,
            timeChangeReceiver,
            intentFilterTimeChange,
            ContextCompat.RECEIVER_NOT_EXPORTED
        )

        val intentFilterDateChange = IntentFilter()
        intentFilterDateChange.addAction(Intent.ACTION_DATE_CHANGED)
        intentFilterDateChange.addAction(Intent.ACTION_TIMEZONE_CHANGED)
        intentFilterDateChange.addAction(Intent.ACTION_LOCALE_CHANGED)

        dateChangeReceiver = DateBroadcast {
            mainViewModel.updateDate()
        }
        registerReceiver(dateChangeReceiver, intentFilterDateChange)
    }
}