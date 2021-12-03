package com.example.springkotlinuseroauth2postgresql.domains.exceptions

class NotFoundException(
    override val message: String?
) : RuntimeException(message) {
}