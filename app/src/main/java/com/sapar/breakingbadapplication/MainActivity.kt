package com.sapar.breakingbadapplication

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.findNavController

import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.sapar.breakingbadapplication.Registration.DBHelper
import com.sapar.breakingbadapplication.Registration.Session
import com.sapar.breakingbadapplication.Registration.User
import com.sapar.breakingbadapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding //using view binding
    lateinit var navController: NavController
    lateinit var handler: DBHelper
    lateinit var session: Session
    private lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var email: String
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        session = Session(this)

        setToolbar()
        init()
        initBtnNavView()
    }
    private fun setToolbar() {
        setSupportActionBar(binding.toolbar)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun init(){


        handler = DBHelper(this)
//        Toast.makeText(this, "data just ${handler.readData(email)?.name.toString()}", Toast.LENGTH_SHORT).show()
    }



    private fun initBtnNavView(){
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.findNavController()

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.charactersFragment, R.id.settingsFragment, R.id.profileFragment2
            ),
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.btnNavView.setupWithNavController(navController)



        binding.fab.setOnClickListener {
            val action = navController.navigate(R.id.profileFragment2)

//            if(binding.btnNavView.menu.getItem(0).isChecked){
//                binding.btnNavView.menu.getItem(0).isChecked = false
//            }
//            else{
//                binding.btnNavView.menu.getItem(2).isChecked = false
//            }
        }

        binding.btnNavView.background = null
        binding.btnNavView.menu.getItem(1).isEnabled = false

    }

    fun goToHomeFragment(){
        val action = navController.navigate(R.id.charactersFragment)
        binding.btnNavView.menu.getItem(0).isChecked = true
    }

//    override fun onSupportNavigateUp(): Boolean {
//
//        return navController.navigateUp() || super.onSupportNavigateUp()
//    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}