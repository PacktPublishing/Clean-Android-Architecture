package com.clean.exercise201

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel(private val adder: NumberAdder = NumberAdder()) : ViewModel() {

    var resultState by mutableStateOf("0")
        private set

    fun add(a: String, b: String) {
        viewModelScope.launch {
            val result = adder.add(a.toInt(), b.toInt())
            resultState = result.toString()
        }
    }
}