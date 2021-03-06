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

        binding.textViewProfileMail.text = session.getUserId().toString()
        binding.textViewProfileName.text = handler.readData(session.getUserId().toString())

    }



    companion object {

        @JvmStatic
        fun newInstance() = ProfileFragment()

    }
}