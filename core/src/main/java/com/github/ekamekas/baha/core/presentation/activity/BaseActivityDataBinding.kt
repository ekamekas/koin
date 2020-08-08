package com.github.ekamekas.baha.core.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

/**
 * Class of [AppCompatActivity] with data binding component
 */
abstract class BaseActivityDataBinding<VM: ViewModel, BIND: ViewDataBinding>: DaggerAppCompatActivity() {

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
     * Setup activity views
     */
    abstract fun setupViews()

    /**
     * Process after setup is done
     */
    abstract fun didSetup()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(viewModelClazz)
        dataBinding = DataBindingUtil.setContentView(this, resourceId)
        setupBinding()
        setupObservers()
        setupViews()
        didSetup()
        dataBinding.lifecycleOwner = this
    }

}