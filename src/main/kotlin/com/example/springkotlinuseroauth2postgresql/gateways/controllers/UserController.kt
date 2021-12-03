package com.example.springkotlinuseroauth2postgresql.gateways.controllers

import com.example.springkotlinuseroauth2postgresql.configs.OpenApiConfig
import com.example.springkotlinuseroauth2postgresql.gateways.controllers.dtos.UserResponseDto
import com.example.springkotlinuseroauth2postgresql.usecases.FindAllUsersUseCase
import com.example.springkotlinuseroauth2postgresql.usecases.FindUserByEmailUseCase
import com.example.springkotlinuseroauth2postgresql.usecases.GetNumberOfUsersUseCase
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("users")
@SecurityRequirement(name = OpenApiConfig.SECURITY_REQUIREMENT)
class UserController(
    private val getNumberOfUsers: GetNumberOfUsersUseCase,
    private val findAllUsers: FindAllUsersUseCase,
    private val findUserByEmail: FindUserByEmailUseCase
) {

    @GetMapping("/count")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    fun getNumberOfUsers() = getNumberOfUsers.count()

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    fun findAllUsers(): List<UserResponseDto> {
        return findAllUsers.find().map {user -> UserResponseDto.fromDomain(user) }
    }

    @GetMapping("/email/{email}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    fun findUserByEmail(@PathVariable("email") email: String): UserResponseDto {
        val user = findUserByEmail.find(email)
        return UserResponseDto.fromDomain(user)
    }
}