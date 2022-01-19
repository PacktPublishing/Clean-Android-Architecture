package com.clean.exercise0401

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class MainTextFormatter @Inject constructor(@ApplicationContext private val applicationContext: Context) {

    fun getCounterText(count: Int) =
        applicationContext.getString(R.string.total_request_count, count)
}