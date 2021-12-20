package com.sapar.breakingbadapplication.Home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.graphics.Color
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import com.sapar.breakingbadapplication.MainActivity
import com.sapar.breakingbadapplication.R
import com.sapar.breakingbadapplication.Registration.RegistrationActivity
import com.sapar.breakingbadapplication.Registration.Session
import com.sapar.breakingbadapplication.databinding.FragmentSettingsBinding
import com.sapar.breakingbadapplication.databinding.FragmentSignUpBinding


class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    var session: Session? = null
    lateinit var prefs: SharedPreferences
    lateinit var editor: SharedPreferences.Editor



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSettingsBinding.bind(view)
        init()
    }

    @SuppressLint("CommitPrefEdits")
    private fun init() {
        prefs = requireActivity().getSharedPreferences("myapp", Context.MODE_PRIVATE)
        editor = prefs.edit()

        session = Session((activity as MainActivity))

        binding.buttonLogOut.setOnClickListener {
            session!!.setLoggedIn(false)
            startActivity(Intent(context, RegistrationActivity::class.java))
            (activity as MainActivity).finish()
        }
        binding.imageButtonShareApp.setOnClickListener {
            try {
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "text/plain"
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Oralbek")
                var shareMessage = "\nLet me send this application\n\n"
                shareMessage =
                    """
                        ${shareMessage}https://play.google.com/store/apps/details?id=${requireActivity().packageName}
                        
                        
                        """.trimIndent()
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
                startActivity(Intent.createChooser(shareIntent, "Choose"))
            } catch (e: Exception) {
            }
        }

        binding.imageButtonChangeBcg.setOnClickListener{
            if (prefs.getBoolean("alreadyChanged", false)){
                (activity as MainActivity).setTheme(R.style.AppThemeDay)
                editor.putBoolean("alreadyChanged", false)
                editor.commit()
            }else{
                (activity as MainActivity).setTheme(R.style.AppThemeNight)
                editor.putBoolean("alreadyChanged", true)
                editor.commit()
            }
            (activity as MainActivity).goToHomeFragment()


        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            SettingsFragment()

    }
}