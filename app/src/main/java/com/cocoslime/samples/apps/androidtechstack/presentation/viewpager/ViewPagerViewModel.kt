package com.cocoslime.samples.apps.androidtechstack.presentation.viewpager

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class ViewPagerViewModel: ViewModel() {

    var text by mutableStateOf("")
}
