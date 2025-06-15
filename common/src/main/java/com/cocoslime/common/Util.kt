package com.cocoslime.common

import android.net.Uri
import android.util.Log

fun String.printlnDebug() {
    if (BuildConfig.DEBUG) println(this)
}

fun String.logDebug(tag: String) {
    Log.d(tag, this)
}

fun Uri.printlnDebug() {
    println("Uri: ${this.toString()}")

    val scheme = this.scheme
    val host = this.host
    val path = this.path
    val query = this.query

    println("Scheme: $scheme")
    println("Host: $host")
    println("Path: $path")
    println("Query: $query")
}
