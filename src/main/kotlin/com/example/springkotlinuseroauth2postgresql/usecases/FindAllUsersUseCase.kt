package com.example.springkotlinuseroauth2postgresql.usecases

import com.example.springkotlinuseroauth2postgresql.domains.User
import com.example.springkotlinuseroauth2postgresql.usecases.ports.UserRepositoryPort
import org.springframework.stereotype.Service

@Service
class FindAllUsersUseCase(
    private val userRepositoryPort: UserRepositoryPort
) {

    fun find(): List<User> {
        return userRepositoryPort.findAll()
    }

}