package com.example.springkotlinuseroauth2postgresql.usecases.ports

import com.example.springkotlinuseroauth2postgresql.domains.User

interface UserRepositoryPort {

    fun getNumberOfUsers(): Int
    fun findAll(): List<User>
    fun findUserByEmail(email: String): User
}