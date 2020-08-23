package ru.kapitoxa.mywallet

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import ru.kapitoxa.mywallet.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setupNavigation()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun setupNavigation() {
        navController = this.findNavController(R.id.nav_host_fragment)

        NavigationUI.setupActionBarWithNavController(this, navController)
        binding.bottomNavView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            val toolbar = supportActionBar ?: return@addOnDestinationChangedListener
            if (destination.id == R.id.operationDetailFragment
                    || destination.id == R.id.categoryDetailFragment) {
                toolbar.setHomeAsUpIndicator(R.drawable.ic_baseline_close_24)
                toolbar.elevation = resources.getDimension(R.dimen.toolbar_no_elevation)
                toolbar.title = null
            } else {
                toolbar.setIcon(R.drawable.ic_baseline_arrow_back_24)
                toolbar.elevation = resources.getDimension(R.dimen.toolbar_standard_elevation)
            }
        }
    }
}