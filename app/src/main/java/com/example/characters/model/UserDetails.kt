package com.example.characters.model

import com.google.gson.annotations.SerializedName

data class UserDetails(
     val id : Int,
     val name: String,
     @SerializedName("images")
     val userPhoto : List<String>,
     @SerializedName("page")
     val pageHttp : String,
)