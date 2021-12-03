package com.example.springkotlinuseroauth2postgresql.usecases

import com.example.springkotlinuseroauth2postgresql.usecases.ports.UserRepositoryPort
import org.springframework.stereotype.Service

@Service
class GetNumberOfUsersUseCase(
    private val userRepositoryPort: UserRepositoryPort
) {

    fun count(): Int {
        return userRepositoryPort.getNumberOfUsers()
    }

}