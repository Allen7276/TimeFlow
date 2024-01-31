package com.apollo.timeflow.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.apollo.timeflow.R
import com.apollo.timeflow.databinding.FragmentSettingsBinding
import com.apollo.timeflow.lifecycleUtils.LifecycleUtils.quickStartCoroutineScope
import com.apollo.timeflow.ui.adapter.SettingAdapter
import com.apollo.timeflow.ui.uiState.SettingUIState
import com.apollo.timeflow.viewmodel.MainViewModelProviderFactory
import com.apollo.timeflow.viewmodel.SettingDataViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest

class SettingFragment : BaseFragment<FragmentSettingsBinding>() {
    override val layoutId: Int = R.layout.fragment_settings

    private lateinit var settingDataViewModel: SettingDataViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        settingDataViewModel = ViewModelProvider(
            this,
            MainViewModelProviderFactory()
        )[SettingDataViewModel::class.java]

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.root.viewTreeObserver.addOnWindowFocusChangeListener { hasFocus ->
            activity?.onWindowFocusChanged(hasFocus)
        }

        val onBackPressedDispatcherCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity?.onWindowFocusChanged(true)
                routeViewModel?.navigateToTimeFragment()
            }
        }

        activity?.onBackPressedDispatcher?.addCallback(
            this.viewLifecycleOwner,
            onBackPressedDispatcherCallback,
        )

        quickStartCoroutineScope(Dispatchers.Main) {
            settingDataViewModel.items.collectLatest(::updateUI)
        }

        settingDataViewModel.fetchSettingItems()
    }

    private fun updateUI(items: List<SettingUIState>?) {
        if (items.isNullOrEmpty()) {
            routeViewModel?.navigateToTimeFragment()
            return
        }

        binding.rvSettings.adapter = SettingAdapter(items)
        binding.rvSettings.layoutManager = LinearLayoutManager(context)
    }

    companion object {
        fun newInstance(): SettingFragment = synchronized(this) {
            SettingFragment()
        }
    }
}