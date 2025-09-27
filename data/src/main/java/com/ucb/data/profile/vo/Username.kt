package com.ucb.data.profile.vo

@JvmInline
value class Username private constructor(val value: String) {
    companion object {
        private val REGEX = Regex("^[a-zA-Z0-9._-]{3,20}$")
        fun create(raw: String): Username {
            val v = raw.trim()
            require(v.isNotEmpty() && REGEX.matches(v)) { "Username inválido" }
            return Username(v.lowercase())
        }
    }
    override fun toString() = value
}
