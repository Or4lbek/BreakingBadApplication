package com.sapar.breakingbadapplication

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.sapar.breakingbadapplication.Registration.DBHelper
import com.sapar.breakingbadapplication.Registration.Session
import com.sapar.breakingbadapplication.Registration.User
import com.sapar.breakingbadapplication.databinding.FragmentProfileBinding
import com.sapar.breakingbadapplication.databinding.FragmentSignUpBinding


class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    lateinit var handler: DBHelper
    lateinit var session: Session


    @RequiresApi(Build.VERSION_CODES.P)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProfileBinding.bind(view)
        init()
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun init() {
        handler = DBHelper(requireContext())
        session = Session(requireContext())

//        var user: User? =  getUserData(session.getUserId())
//        binding.textViewProfileMail.text = user?.email.toString()
//        binding.textViewProfileName.text = user?.name.toString()

    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun getUserData(email:String): User? {
//        return handler.readData(session.getUserId())
        val cursor = handler.readData(session.getUserId())

        return handler.readData(email)

    }

    companion object {

        @JvmStatic
        fun newInstance() = ProfileFragment()

    }
}