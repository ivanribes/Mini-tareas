package com.ivanribes.tareasapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform