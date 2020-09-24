package com.ludovic.vimont.lydiarandomuser.model

/**
 * The response of the Random User Api
 * @see RandomUserAPI
 */
data class RandomUserResponse(
    val results: List<User>,
)