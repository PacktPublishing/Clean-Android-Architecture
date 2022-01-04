package com.clean.exercise202

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel(private val adder: NumberAdder = NumberAdder()) : ViewModel() {

    var resultState by mutableStateOf("0")
        private set

    fun add(a: String, b: String) {
        viewModelScope.launch {
            adder.add(a.toInt(), b.toInt())
                .collect {
                    resultState = it.toString()
                }
        }
    }
}