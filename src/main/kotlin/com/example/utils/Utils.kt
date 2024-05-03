package com.example.utils

fun String.toCategoryId(): String {
    PlaceMarkCategory.values().forEach {
        this.replace(it.categoryType, it.categoryId)
    }
    return this
}

fun String.toCategoryType(): String {
    PlaceMarkCategory.values().forEach {
        this.replace(it.categoryId, it.categoryType)
    }
    return this
}

fun List<String>.convertToCategoryId(): List<String> {
    this.forEach {
        it.toCategoryId()
    }
    return this
}

fun List<String>.convertToCategoryType(): List<String> {
    this.forEach {
        it.toCategoryType()
    }
    return this
}