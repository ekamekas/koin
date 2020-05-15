package com.github.ekamekas.baha.core.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

/**
 * Class of [AppCompatActivity] with view model component
 */
abstract class BaseActivityViewModel<VM: ViewModel>: DaggerAppCompatActivity() {

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
     * Setup activity views
     */
    abstract fun setupViews()

    /**
     * Process after setup is done
     */
    abstract fun didSetup()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(resourceId)
        viewModel = ViewModelProvider(this, viewModelFactory).get(viewModelClazz)
        setupObservers()
        setupViews()
        didSetup()
    }

}