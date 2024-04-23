package com.apollo.timeflow.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.apollo.timeflow.viewmodel.MainViewModelProviderFactory
import com.apollo.timeflow.viewmodel.RouteViewModel

abstract class BaseFragment<Binding : ViewDataBinding> : Fragment() {
    abstract val layoutId: Int

    protected lateinit var binding: Binding
        private set

    protected var routeViewModel: RouteViewModel? = null
        private set

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        routeViewModel = activity?.let {
            ViewModelProvider(
                it,
                MainViewModelProviderFactory()
            )[RouteViewModel::class.java]
        }

        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        return binding.root
    }
}