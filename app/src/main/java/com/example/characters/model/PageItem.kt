package com.example.characters.model

sealed class PageItem<out T> {

    data class Element<T>(val data: T) : PageItem<T>()

    object Loading : PageItem<Nothing>()

}
