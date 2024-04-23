package com.apollo.timeflow.ui

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.apollo.timeflow.DateBroadcast
import com.apollo.timeflow.R
import com.apollo.timeflow.TimeBroadcast
import com.apollo.timeflow.databinding.FragmentTimeBinding
import com.apollo.timeflow.imageHash
import com.apollo.timeflow.lifecycleUtils.LifecycleUtils.quickStartCoroutineScope
import com.apollo.timeflow.viewmodel.MainViewModelProviderFactory
import com.apollo.timeflow.viewmodel.TimeDataViewModel
import kotlinx.coroutines.flow.collectLatest
import java.util.Calendar

class TimeFragment : BaseFragment<FragmentTimeBinding>() {
    override val layoutId: Int = R.layout.fragment_time

    private lateinit var dataViewModel: TimeDataViewModel
    private lateinit var timeChangeReceiver: TimeBroadcast
    private lateinit var dateChangeReceiver: DateBroadcast

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataViewModel = ViewModelProvider(
            this,
            MainViewModelProviderFactory()
        )[TimeDataViewModel::class.java]
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.root.viewTreeObserver.addOnWindowFocusChangeListener { hasFocus ->
            activity?.onWindowFocusChanged(hasFocus)
        }

        val mContext = context ?: return

        handleViewModel()
        updateTimeFormat()

        binding.materialCardView.setOnClickListener {
            // 2.x-release的功能，暂不开放，后续维护
//             routeViewModel?.navigateToSettingFragment()

            // 1.x and 2.x-beta 的功能，升级2.x-release，后续会删除这个交互，放入Setting中
            dataViewModel.isDateShow(
                binding.tvDate.visibility != View.VISIBLE
            )
        }

        binding.clockCardView.setOnClickListener {
            // 1.x and 2.x-beta的功能，升级2.x-release会删除这个交互，放入Setting中
            dataViewModel.timeFormatRecordUpdate(
                binding.tvTimeFormatInAlarmActivity.visibility in setOf(
                    View.VISIBLE
                )
            )
            dataViewModel.updateTime()
        }

        val intentFilterTimeChange = IntentFilter()
        intentFilterTimeChange.addAction(Intent.ACTION_TIME_TICK)
        intentFilterTimeChange.addAction(Intent.ACTION_TIME_CHANGED)
        intentFilterTimeChange.addAction(Intent.ACTION_TIMEZONE_CHANGED)
        intentFilterTimeChange.addAction(Intent.ACTION_LOCALE_CHANGED)

        timeChangeReceiver = TimeBroadcast {
            dataViewModel.updateTime()
        }

        ContextCompat.registerReceiver(
            mContext,
            timeChangeReceiver,
            intentFilterTimeChange,
            ContextCompat.RECEIVER_NOT_EXPORTED
        )

        val intentFilterDateChange = IntentFilter()
        intentFilterDateChange.addAction(Intent.ACTION_DATE_CHANGED)
        intentFilterDateChange.addAction(Intent.ACTION_TIMEZONE_CHANGED)
        intentFilterDateChange.addAction(Intent.ACTION_LOCALE_CHANGED)

        dateChangeReceiver = DateBroadcast(dataViewModel)
        activity?.registerReceiver(dateChangeReceiver, intentFilterDateChange)

        dataViewModel.updateDate()
        dataViewModel.updateTime()
    }

    override fun onResume() {
        super.onResume()
        dataViewModel.updateTime()
    }

    private fun handleViewModel() {
        quickStartCoroutineScope {
            dataViewModel.topLeftModelFlowOf.collectLatest {
                binding.topLeftAlarmNumberInAlarmActivity.setImageResource(imageHash[it]!!)
            }
        }

        quickStartCoroutineScope {
            dataViewModel.topRightModelFlowOf.collectLatest {
                binding.topRightAlarmNumberInAlarmActivity.setImageResource(imageHash[it]!!)
            }
        }

        quickStartCoroutineScope {
            dataViewModel.bottomLeftModelFlowOf.collectLatest {
                binding.boottomLeftAlarmNumberInAlarmActivity.setImageResource(imageHash[it]!!)
            }
        }

        quickStartCoroutineScope {
            dataViewModel.bottomRightModelFlowOf.collectLatest {
                binding.bottomRightAlarmNumberInAlarmActivity.setImageResource(imageHash[it]!!)
            }
        }

        quickStartCoroutineScope {
            dataViewModel.currentDate.collectLatest {
                binding.tvDate.text = it
            }
        }

        quickStartCoroutineScope {
            dataViewModel.timeFormat.collectLatest {
                updateTimeFormat()
                dataViewModel.updateTime()
            }
        }

        quickStartCoroutineScope {
            dataViewModel.isDateShowDataStoreFlow.collectLatest {
                binding.tvDate.visibility = if (it) View.VISIBLE else View.GONE
                dataViewModel.updateDate()
            }
        }

        quickStartCoroutineScope {
            dataViewModel.timeFormatRecordDataStoreFlow.collectLatest {
                binding.tvTimeFormatInAlarmActivity.visibility =
                    if (it) View.GONE else View.VISIBLE
                dataViewModel.editTimeFormat(if (it) TimeDataViewModel.TimeFormat.Base24 else TimeDataViewModel.TimeFormat.Base12)
            }
        }
    }

    private fun updateTimeFormat() {
        binding.tvTimeFormatInAlarmActivity.text =
            if (Calendar.getInstance().get(Calendar.HOUR_OF_DAY) >= 12) {
                this.context?.getString(R.string.pm)
            } else {
                this.context?.getString(R.string.am)
            }
    }

    companion object {
        fun newInstance(): TimeFragment = synchronized(this) {
            TimeFragment()
        }
    }
}