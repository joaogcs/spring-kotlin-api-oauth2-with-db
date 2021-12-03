package com.example.springkotlinuseroauth2postgresql.gateways.repositories.postgresql

import com.example.springkotlinuseroauth2postgresql.gateways.repositories.postgresql.models.UsersModel
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<UsersModel, String> {

    fun findByEmail(email: String): Optional<UsersModel>

}