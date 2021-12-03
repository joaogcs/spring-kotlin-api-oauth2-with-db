package com.example.springkotlinuseroauth2postgresql.usecases

import com.example.springkotlinuseroauth2postgresql.domains.User
import com.example.springkotlinuseroauth2postgresql.usecases.ports.UserRepositoryPort
import org.springframework.stereotype.Service

@Service
class FindUserByEmailUseCase(
    private val userRepositoryPort: UserRepositoryPort
) {

    fun find(email: String): User {
        return userRepositoryPort.findUserByEmail(email)
    }

}