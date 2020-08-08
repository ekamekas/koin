package com.github.ekamekas.baha.core.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.android.support.DaggerFragment

/**
 * Class of [Fragment] with modification required for core architecture
 */
abstract class BaseFragment: DaggerFragment() {

    abstract val resourceId: Int

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
        setupViews()
        didSetup()
    }

}