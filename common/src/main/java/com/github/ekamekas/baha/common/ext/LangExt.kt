package com.github.ekamekas.baha.common.ext

/**
 * Force 'when' to declare all branches with 'else'
 */
val <T> T.exhaustive: T
    get() = this