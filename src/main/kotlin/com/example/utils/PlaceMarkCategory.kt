package com.example.utils

enum class PlaceMarkCategory(val categoryId: String, val categoryType: String) {
    PAPER(categoryId = "0", categoryType = "paper"),
    PLASTIC(categoryId = "1", categoryType = "plastic"),
    GLASS(categoryId = "2", categoryType = "glass"),
    METAL(categoryId = "3", categoryType = "metal"),
    TETRA_PACK(categoryId = "4", categoryType = "tetra_pack"),
    CLOTHES(categoryId = "5", categoryType = "clothes"),
    LAMPS(categoryId = "6", categoryType = "lamps"),
    CAPS(categoryId = "7", categoryType = "caps"),
    TECH(categoryId = "8", categoryType = "tech"),
    BATTERIES(categoryId = "9", categoryType = "batteries"),
    TIRES(categoryId = "10", categoryType = "tires"),
    DANGEROUS(categoryId = "11", categoryType = "dangerous"),
    OTHER(categoryId = "12", categoryType = "other")
}