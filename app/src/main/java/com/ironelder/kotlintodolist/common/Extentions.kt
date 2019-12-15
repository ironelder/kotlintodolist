package com.ironelder.kotlintodolist.common

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity

fun AppCompatActivity.hideKeyboard(view:View) {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}