package com.sapar.breakingbadapplication

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {

    @GET("characters")
    fun getData():Call<List<CharacterItem>>

    @GET("characters/{char_id}")
    fun getCharacterById(@Path("char_id") char_id:Int): Call<List<CharacterItem>>
}