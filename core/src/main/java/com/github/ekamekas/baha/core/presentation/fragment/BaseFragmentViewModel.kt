package com.github.ekamekas.baha.core.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerFragment
import javax.inject.Inject

/**
 * Class of [Fragment] with view model component
 * Remember to provide this fragment in the activity parent
 */
abstract class BaseFragmentViewModel<VM: ViewModel>: DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    protected lateinit var viewModel: VM
    protected abstract val viewModelClazz: Class<VM>

    abstract val resourceId: Int

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
        return inflater.inflate(resourceId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity(), viewModelFactory).get(viewModelClazz)
        setupObservers()
        setupViews()
        didSetup()
    }

}