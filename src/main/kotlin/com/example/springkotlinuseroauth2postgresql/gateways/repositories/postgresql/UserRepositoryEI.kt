package com.example.springkotlinuseroauth2postgresql.gateways.repositories.postgresql

import com.example.springkotlinuseroauth2postgresql.domains.User
import com.example.springkotlinuseroauth2postgresql.domains.exceptions.NotFoundException
import com.example.springkotlinuseroauth2postgresql.usecases.ports.UserRepositoryPort
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryEI(
    private val userRepository: UserRepository
) : UserRepositoryPort {

    override fun getNumberOfUsers(): Int {
        return userRepository.findAll().count()
    }

    override fun findAll(): List<User> {
        return userRepository.findAll().map {userModel -> userModel.toDomain() }
    }

    override fun findUserByEmail(email: String): User {
        return userRepository.findByEmail(email).orElseThrow{ NotFoundException("user not found") }.toDomain()
    }
}