package com.example.grab.app.model

data class User(
    val userId: String = "",
    val username: String = "",
    val name: String = "",
    val email: String = "",
    val photoUrl: String = "",
    val bio: String = "",
    val gender: String = "",
    val followersCount: Int = 0,
    val followingCount: Int = 0,
    val totalLikesReceived: Int = 0
)