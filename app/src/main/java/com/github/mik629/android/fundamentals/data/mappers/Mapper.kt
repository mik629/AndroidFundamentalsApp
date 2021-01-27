package com.github.mik629.android.fundamentals.data.mappers

interface Mapper<T, V> {
    fun map(obj: T): V

    fun reverseMap(obj: V): T
}
