package com.github.ekamekas.baha.core.domain.use_case

import com.github.ekamekas.baha.core.domain.entity.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Contract defined for use case implementation
 */
abstract class BaseUseCase<out Type, in Params> where Type: Any? {

    protected abstract suspend fun run(params: Params): Result<Type>
    abstract suspend fun cancel()

    suspend fun invoke(params: Params) =
        withContext(Dispatchers.IO) {
            return@withContext run(params)
        }

}