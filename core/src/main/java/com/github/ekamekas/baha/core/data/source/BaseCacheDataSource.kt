package com.github.ekamekas.baha.core.data.source

import com.github.ekamekas.baha.core.domain.entity.Result
import java.util.concurrent.ConcurrentHashMap

/**
 * Contract defined for cache data source implementation
 */
abstract class BaseCacheDataSource<T> {

    private var cachedData: ConcurrentHashMap<String, Pair<Long, T>>? = null  // value will be pair of created time and data
    private var expirationOffsetMillis = 3600000L  // offset millis of expiration time, default will be 1 hour

    /**
     * Setter of expiration millis offset
     */
    fun setExpirationOffsetMillis(millis: Long) {
        if(millis < 0) {
            throw IllegalStateException("offset must be zero or positive number")
        }
        expirationOffsetMillis = millis
    }

    @Throws(Exception::class)
    protected open fun deleteCache(): Result<Nothing?> {
        cachedData?.clear()
            ?: throw IllegalStateException("Data is empty")

        return Result.Success(null)
    }

    @Throws(Exception::class)
    protected open fun deleteCache(key: String): Result<Nothing?> {
        cachedData?.remove(key)
            ?: throw IllegalStateException("Data is not found")

        return Result.Success(null)
    }

    @Throws(Exception::class)
    protected open fun fetchCache(): Result<List<T>> {
        val result = cachedData?.map { it.value }
            ?: throw IllegalStateException("Data not found")

        result.map { it.first }.min()?.plus(expirationOffsetMillis)?.also { minimumExpirationMillis ->
            if(minimumExpirationMillis < System.currentTimeMillis()) {
                throw IllegalStateException("Data has been expired $minimumExpirationMillis")
            }
        } ?: throw IllegalStateException("Data is empty")

        return Result.Success(result.map { it.second })
    }

    @Throws(Exception::class)
    protected open fun fetchCache(key: String): Result<T> {
        val result = cachedData?.get(key)
            ?: throw IllegalStateException("Data not found")

        result.first.plus(expirationOffsetMillis).also { expirationMillis ->
            if(expirationMillis < System.currentTimeMillis()) {  // data has been expired
                throw IllegalStateException("Data has been expired ${result.first}")
            }
        }

        return Result.Success(result.second)
    }

    @Throws(Exception::class)
    protected open fun upsertCache(key: String, data: T): Result<Nothing?> {
        if(cachedData == null) {
            cachedData = ConcurrentHashMap()
        }
        cachedData?.also { cache ->
            cache[key] = Pair(System.currentTimeMillis(), data)
        }

        return Result.Success(null)
    }

    @Throws(Exception::class)
    protected open fun upsertCache(data: Map<String, T>): Result<Nothing?> {
        data.keys.forEach { key ->
            data[key]?.also { upsertCache(key, it) }
        }

        return Result.Success(null)
    }

}