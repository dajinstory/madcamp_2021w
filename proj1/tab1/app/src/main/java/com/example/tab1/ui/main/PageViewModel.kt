package com.example.tab1.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PageViewModel : ViewModel() {

    private val _index = MutableLiveData<Int>()

    // LiveData according to _index
    val tab_index: LiveData<Int> = Transformations.map(_index) { it }

    fun setIndex(index: Int) {
        _index.value = index
    }
}