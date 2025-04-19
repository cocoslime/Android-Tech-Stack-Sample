package com.cocoslime.presentation.navigation.fragment.xml

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.NavHostFragment
import com.cocoslime.presentation.R
import com.cocoslime.presentation.databinding.ActivityFragmentNavBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class XmlFragmentNavActivity : FragmentActivity() {

    private var _binding: ActivityFragmentNavBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        _binding = ActivityFragmentNavBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        // 네비게이션 그래프 설정
        val navController = navHostFragment.navController
        val navGraph = navController.navInflater.inflate(R.navigation.fragment_nav)
        navController.graph = navGraph
    }
}
