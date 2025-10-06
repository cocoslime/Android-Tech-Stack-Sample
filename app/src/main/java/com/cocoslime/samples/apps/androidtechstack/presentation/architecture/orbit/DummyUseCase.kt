package com.cocoslime.samples.apps.androidtechstack.presentation.architecture.orbit

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DummyUseCase @Inject constructor() {

    suspend fun getOrbitTitle() = withContext(Dispatchers.IO){
        delay(500L)
        "Hello World"
    }

    suspend fun getOrbitImageUrl() = withContext(Dispatchers.IO) {
        val randId = (1..1000).random()
        delay(500L)
        "https://picsum.photos/id/$randId/200/300"
    }
}
