package com.androidhuman.example.simplegithub.api.model

import com.google.gson.annotations.SerializedName

class GithubAccessToken(
        @field:SerializedName("access_token") val accessToken: String,
        val scope: String,
        @field:SerializedName("token_type") val tokenType: String)
