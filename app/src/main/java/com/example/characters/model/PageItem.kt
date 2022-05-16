package com.example.characters.model

sealed class PageItem<T> {

    class Context<T>(val data: T) : PageItem<T>()

    object Loading : PageItem<Nothing>()

}
