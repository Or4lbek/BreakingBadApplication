package com.sapar.breakingbadapplication.Registration

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import com.sapar.breakingbadapplication.MainActivity
import com.sapar.breakingbadapplication.R
import com.sapar.breakingbadapplication.databinding.FragmentSignInBinding


class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding?= null
    private val binding get() = _binding!!
    lateinit var handler:DBHelper
    lateinit var session: Session


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSignInBinding.bind(view)
        init()
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun init() {
        session = Session(requireContext())
        handler = DBHelper(requireContext())
        binding.buttonSignInLog.setOnClickListener {
            val checkUsersPassword: Boolean =
                handler.checkUsersPassword(
                    binding.editTextTextEmailAddress.text.toString(),
                    binding.editTextTextPassword.text.toString()
                )
            Log.i("check3", checkUsersPassword.toString())
            if (checkUsersPassword) {
//                session.setLoggedIn(true)
                session.setLoggedIn(true)
                Toast.makeText(context, "Sign in successful", Toast.LENGTH_SHORT).show()
                session.setUserId(binding.editTextTextEmailAddress.text.toString())
//                intent.putExtra("email", binding.editTextTextEmailAddress.text.toString())
                val intent = Intent(context, MainActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            } else {
                Toast.makeText(context, "Invalid Credentials", Toast.LENGTH_SHORT).show()
            }
        }

        binding.buttonGoReg.setOnClickListener {
            val action = SignInFragmentDirections.actionSignInFragmentToSignUpFragment()
            findNavController().navigate(action)
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SignInFragment()

    }
}