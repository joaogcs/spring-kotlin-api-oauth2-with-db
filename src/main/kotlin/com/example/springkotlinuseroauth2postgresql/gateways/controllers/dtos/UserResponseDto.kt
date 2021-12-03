package com.example.springkotlinuseroauth2postgresql.gateways.controllers.dtos

import com.example.springkotlinuseroauth2postgresql.domains.User

class UserResponseDto(
    val username: String,
    val email: String,
    val enabled: Boolean,
) {
    companion object {
        fun fromDomain(user: User): UserResponseDto {
            return UserResponseDto(
                username = user.username,
                email = user.email,
                enabled = user.enabled,
            )
        }
    }
}