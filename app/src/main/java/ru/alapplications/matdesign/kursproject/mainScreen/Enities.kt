package ru.alapplications.matdesign.kursproject.mainScreen

data class Category(var categoryType: Int, var header: Header, var items: List<Item>?)
data class Item (val title: String, val imageId: Int)
data class Header (val title: String, val imageId: Int)
