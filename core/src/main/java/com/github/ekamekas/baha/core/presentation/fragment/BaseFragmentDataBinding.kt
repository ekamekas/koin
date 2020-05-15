package com.github.ekamekas.baha.core.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerFragment
import javax.inject.Inject

/**
 * Class of [Fragment] with data binding component
 * Remember to provide this fragment in the activity parent
 */
abstract class BaseFragmentDataBinding<VM: ViewModel, BIND: ViewDataBinding>: DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    protected lateinit var viewModel: VM
    protected abstract val viewModelClazz: Class<VM>
    protected lateinit var dataBinding: BIND

    abstract val resourceId: Int

    /**
     * Setup view data binding
     */
    abstract fun setupBinding()

    /**
     * Setup observer exposed
     */
    abstract fun setupObservers()

    /**
     * Setup fragment views
     */
    abstract fun setupViews()

    /**
     * Process after setup is done
     */
    abstract fun didSetup()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater, resourceId, container, false)
        dataBinding.lifecycleOwner = viewLifecycleOwner
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity(), viewModelFactory).get(viewModelClazz)
        setupBinding()
        setupObservers()
        setupViews()
        didSetup()
    }

}