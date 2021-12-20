package com.sapar.breakingbadapplication.Registration

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.sapar.breakingbadapplication.MainActivity
import com.sapar.breakingbadapplication.R
import com.sapar.breakingbadapplication.databinding.FragmentSignUpBinding


class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    lateinit var handler: DBHelper
    lateinit var session: Session

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSignUpBinding.bind(view)
        init()
    }

    private fun checkEditText() {
        if (binding.editTextTextEmailAddress.text!!.isEmpty()) {
            makeToast("Fill in the email")
        } else if (binding.editTextTextName.text!!.isEmpty()) {
            makeToast("Fill in the name")
        } else if (binding.editTextTextPassword.text!!.isEmpty()) {
            makeToast("Fill in the password")
        } else if (binding.editTextTextPasswordCheck.text!!.isEmpty()) {
            makeToast("Fill in the password second time")
        } else {
            if (binding.editTextTextPassword.text.toString() != binding.editTextTextPasswordCheck.text.toString()) {
                makeToast("Password not same")

//                false
            }
        }
    }

    private fun checkEditTextToBool(): Boolean {
        if (binding.editTextTextEmailAddress.text!!.isEmpty()) {
            return false
        } else if (binding.editTextTextName.text!!.isEmpty()) {
//            makeToast("Fill in the name")
            return false

        } else if (binding.editTextTextPassword.text!!.isEmpty()) {
//            makeToast("Fill in the password"
            //
            return false

        } else if (binding.editTextTextPasswordCheck.text!!.isEmpty()) {
//            makeToast("Fill in the password second time")
            return false

        } else {
            if (binding.editTextTextPassword.text.toString() != binding.editTextTextPasswordCheck.text.toString()) {
                return false

//                false
            }
            return true
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun init() {
        session = Session(requireContext())
        handler = DBHelper((requireContext()))

        binding.buttonGoLog.setOnClickListener {
            val action = SignUpFragmentDirections.actionSignUpFragmentToSignInFragment()
            findNavController().navigate(action)
        }

        binding.buttonSignUpLog.setOnClickListener {
//            println(checkEditText())
            if (!checkEditTextToBool()) {
                checkEditText()
            } else {
                val checkUser: Boolean =
                    handler.checkUsersEmail(binding.editTextTextEmailAddress.text.toString())
                Log.i("myCheck1", checkUser.toString())
                if (!checkUser) {
                    val insert: Boolean = handler.insertData(
                        binding.editTextTextEmailAddress.text.toString(),
                        binding.editTextTextName.text.toString(),
                        binding.editTextTextPassword.text.toString()
                    )
                    Log.i("myCheck2", insert.toString())
                    if (insert) {
                        handler.insertData(
                            binding.editTextTextEmailAddress.text.toString(),
                            binding.editTextTextName.text.toString(),
                            binding.editTextTextPassword.text.toString())
                       session.setLoggedIn(true)
                        Toast.makeText(
                            context,
                            "REGISTERED SUCCESSFULLY",
                            Toast.LENGTH_SHORT
                        ).show()
                        session.setUserId(binding.editTextTextEmailAddress.text.toString())
                        val intent = Intent(context, MainActivity::class.java)
                        startActivity(intent)
                        requireActivity().finish()
                    } else {
                        Toast.makeText(
                            context,
                            "REGISTRATION FAILED",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        context,
                        "User already exists! Please sign in",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun makeToast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            SignUpFragment()
    }
}