package com.github.ekamekas.baha.core.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.android.support.DaggerAppCompatActivity

/**
 * Class of [AppCompatActivity] with modification required for core architecture
 */
abstract class BaseActivity: DaggerAppCompatActivity() {

    abstract val resourceId: Int

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
        setupViews()
        didSetup()
    }

}