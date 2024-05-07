package com.example.utils

fun String.toCategoryId(): String {
    return when(this) {
        PlaceMarkCategory.PAPER.categoryType -> PlaceMarkCategory.PAPER.categoryId
        PlaceMarkCategory.PLASTIC.categoryType -> PlaceMarkCategory.PLASTIC.categoryId
        PlaceMarkCategory.GLASS.categoryType -> PlaceMarkCategory.GLASS.categoryId
        PlaceMarkCategory.METAL.categoryType -> PlaceMarkCategory.METAL.categoryId
        PlaceMarkCategory.TETRA_PACK.categoryType -> PlaceMarkCategory.TETRA_PACK.categoryId
        PlaceMarkCategory.CLOTHES.categoryType -> PlaceMarkCategory.CLOTHES.categoryId
        PlaceMarkCategory.LAMPS.categoryType -> PlaceMarkCategory.LAMPS.categoryId
        PlaceMarkCategory.CAPS.categoryType -> PlaceMarkCategory.CAPS.categoryId
        PlaceMarkCategory.TECH.categoryType -> PlaceMarkCategory.TECH.categoryId
        PlaceMarkCategory.BATTERIES.categoryType -> PlaceMarkCategory.BATTERIES.categoryId
        PlaceMarkCategory.TIRES.categoryType -> PlaceMarkCategory.TIRES.categoryId
        PlaceMarkCategory.DANGEROUS.categoryType -> PlaceMarkCategory.DANGEROUS.categoryId
        PlaceMarkCategory.OTHER.categoryType -> PlaceMarkCategory.OTHER.categoryId
        else -> PlaceMarkCategory.OTHER.categoryId
    }
}

fun String.toCategoryType(): String {
    return when(this) {
        PlaceMarkCategory.PAPER.categoryId -> PlaceMarkCategory.PAPER.categoryType
        PlaceMarkCategory.PLASTIC.categoryId -> PlaceMarkCategory.PLASTIC.categoryType
        PlaceMarkCategory.GLASS.categoryId -> PlaceMarkCategory.GLASS.categoryType
        PlaceMarkCategory.METAL.categoryId -> PlaceMarkCategory.METAL.categoryType
        PlaceMarkCategory.TETRA_PACK.categoryId -> PlaceMarkCategory.TETRA_PACK.categoryType
        PlaceMarkCategory.CLOTHES.categoryId -> PlaceMarkCategory.CLOTHES.categoryType
        PlaceMarkCategory.LAMPS.categoryId -> PlaceMarkCategory.LAMPS.categoryType
        PlaceMarkCategory.CAPS.categoryId -> PlaceMarkCategory.CAPS.categoryType
        PlaceMarkCategory.TECH.categoryId -> PlaceMarkCategory.TECH.categoryType
        PlaceMarkCategory.BATTERIES.categoryId -> PlaceMarkCategory.BATTERIES.categoryType
        PlaceMarkCategory.TIRES.categoryId -> PlaceMarkCategory.TIRES.categoryType
        PlaceMarkCategory.DANGEROUS.categoryId -> PlaceMarkCategory.DANGEROUS.categoryType
        PlaceMarkCategory.OTHER.categoryId -> PlaceMarkCategory.OTHER.categoryType
        else -> PlaceMarkCategory.OTHER.categoryType
    }
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