package com.cocoslime

import com.cocoslime.presentation.BuildConfig

fun String.printlnDebug() {
    if (BuildConfig.DEBUG) println(this)
}