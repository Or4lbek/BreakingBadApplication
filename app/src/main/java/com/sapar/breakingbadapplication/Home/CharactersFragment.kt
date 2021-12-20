package com.sapar.breakingbadapplication.Home

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sapar.breakingbadapplication.ApiInterface
import com.sapar.breakingbadapplication.CharacterItem
import com.sapar.breakingbadapplication.R
import com.sapar.breakingbadapplication.databinding.FragmentCharactersBinding
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.util.ArrayList


class CharactersFragment : Fragment(R.layout.fragment_characters), Adaptery.OnItemNoteListener {
    private var _binding: FragmentCharactersBinding? = null
    private val binding get() = _binding!!
    private val BASE_URL:String = "https://www.breakingbadapi.com/api/"
    var charactersList: ArrayList<CharacterItem> = ArrayList()
    lateinit var charactersAdapter:Adaptery
    private lateinit var linearLayoutManager: LinearLayoutManager
    var char_id: Int = 1

    private lateinit var name: String
    lateinit var prefs: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCharactersBinding.bind(view)
        init()
    }

    @SuppressLint("CommitPrefEdits")
    private fun init() {
        prefs = requireActivity().getSharedPreferences("myapp", Context.MODE_PRIVATE)
        editor = prefs.edit()

        linearLayoutManager = LinearLayoutManager(context)

        binding.recyclerView.setLayoutManager(linearLayoutManager)
        charactersAdapter = Adaptery(charactersList, requireContext(), this)
        binding.recyclerView.adapter = charactersAdapter
        getMyData()
    }

    @DelicateCoroutinesApi
    @SuppressLint("NotifyDataSetChanged")
    private fun getMyData() {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiInterface::class.java)
        val retrofitData = retrofitBuilder.getData()

        if (charactersList.isEmpty()){
            binding.progressBar.visibility = View.VISIBLE
        }
        retrofitData.enqueue(object : Callback<List<CharacterItem>> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<List<CharacterItem>>,
                response: Response<List<CharacterItem>>
            ) {
                val responseBody = response.body()!!

                for (character in responseBody){
                    charactersList.add(character)
                    char_id = character.char_id
                    name = character.name
                    charactersAdapter.notifyDataSetChanged()
                    binding.progressBar.visibility = View.GONE

                }

            }

            override fun onFailure(call: Call<List<CharacterItem>>, t: Throwable) {
                binding.progressBar.visibility = View.GONE
                Toast.makeText(context, "Error: " + t.message, Toast.LENGTH_LONG).show()            }
        })
    }

    companion object {

        @JvmStatic
        fun newInstance() = CharactersFragment()

    }

    override fun onNoteClick(position: Int) {
        editor.putInt("id", position)
        editor.commit()
        val action = CharactersFragmentDirections.actionCharactersFragmentToCharacterDetailFragment()
        findNavController().navigate(action)
    }
}