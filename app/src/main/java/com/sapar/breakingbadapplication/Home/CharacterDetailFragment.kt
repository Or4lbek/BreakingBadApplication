package com.sapar.breakingbadapplication.Home

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import com.sapar.breakingbadapplication.ApiInterface
import com.sapar.breakingbadapplication.CharacterItem
import com.sapar.breakingbadapplication.R
import com.sapar.breakingbadapplication.databinding.FragmentCharacterDetailBinding
import com.sapar.breakingbadapplication.databinding.FragmentCharactersBinding
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.character_record_item.*
import kotlinx.android.synthetic.main.fragment_character_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.ArrayList


class CharacterDetailFragment : Fragment(R.layout.fragment_character_detail) {

    private var _binding: FragmentCharacterDetailBinding? = null
    private val binding get() = _binding!!
    private val BASE_URL:String = "https://www.breakingbadapi.com/api/"
    private val listViewOccupation: ListView? = null
    private var occ: List<String>? = null
    private var character: CharacterItem? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCharacterDetailBinding.bind(view)
        getMyData()
    }

    private fun getMyData() {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retrofitBuilder.getCharacterById(1)





        binding.progressBar2.visibility = View.VISIBLE
        retrofitData.enqueue(object : Callback<List<CharacterItem>> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<List<CharacterItem>>,
                response: Response<List<CharacterItem>>,
            ) {
                val responseBody = response.body()!![0]
                val character:CharacterItem = responseBody as CharacterItem
//                Toast.makeText(requireContext(),character.toString(), Toast.LENGTH_SHORT).show()
                binding.progressBar2.visibility = View.GONE

                binding.apply {
                    textViewDName.text = character.name
                    textViewDNick.text = character.nickname
                    textViewDDateOfBirthday.text = character.birthday
                    textViewDStatus.text = character.status
//                    textViewCategory.text = character?.category
                    occ = character.occupation
                    val adapter =
                        ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, occ!!)
                    listViewOccupation.adapter = adapter
                    Picasso.with(requireContext())
                        .load(character.img)
                        .placeholder(R.drawable.back)
                        .fit()
                        .into(imageViewD)

                }
////                val myStringBuilder:StringBuilder = StringBuilder()
//                for (character in responseBody){
////                    myStringBuilder.append(character.name)
////                    myStringBuilder.append("\n")
//                    charactersList.add(character)
//                    char_id = character.char_id
//                    name = character.name
//                    charactersAdapter.notifyDataSetChanged()
//                    binding.progressBar.visibility = View.GONE
//
//                }

//                binding.textViewNames.text = myStringBuilder.toString()
            }

            override fun onFailure(call: Call<List<CharacterItem>>, t: Throwable) {
                Toast.makeText(requireContext(), "some problems", Toast.LENGTH_SHORT).show()
            }
        })
    }


    companion object {

        @JvmStatic
        fun newInstance() =
            CharacterDetailFragment()
    }
}