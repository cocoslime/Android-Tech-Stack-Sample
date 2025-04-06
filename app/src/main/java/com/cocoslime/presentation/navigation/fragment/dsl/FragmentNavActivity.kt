package com.cocoslime.presentation.navigation.fragment.dsl

import android.annotation.SuppressLint
import androidx.navigation.NavController
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.createGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import com.cocoslime.presentation.R
import com.cocoslime.presentation.databinding.ActivityFragmentNavBinding

/*
https://developer.android.com/guide/navigation/design/kotlin-dsl
 */
class FragmentNavActivity : FragmentActivity() {

    private var _binding: ActivityFragmentNavBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        _binding = ActivityFragmentNavBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initNavigation()
    }

    private fun initNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        // findNavController() 로 하면 does not have a NavController set 오류 발생
        val navController = navHostFragment.navController

        navController.graph = navController.createGraph(
            startDestination = FragmentNavRoute.Source
        ) {
            fragment<SourceFragment, FragmentNavRoute.Source> {
                //builder
                label = getString(R.string.source_screen_title)
            }
            /**
             * Args: use Kotiln DSL.
             * Result: Fragment Result API
             */
            fragment<DestinationFragment, FragmentNavRoute.DestinationArgs> {
                label = getString(R.string.destination_screen_title)
            }
            fragment<VmDestinationFragment, FragmentNavRoute.VmDestinationArgs>() {
                label = getString(R.string.vm_destination_screen_title)
            }
        }

        printBackStack(navController)
    }

    @SuppressLint("RestrictedApi")
    private fun printBackStack(navController: NavController) {
        lifecycleScope.launch {
            navController.currentBackStack.collect {
                val routes = it
                    .map { it.destination }
                    .joinToString(separator = "\n")
                "======currentBackStack=======\n${routes}\n==============".also(::println)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
