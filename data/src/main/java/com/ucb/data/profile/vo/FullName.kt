package com.ucb.data.profile.vo

@JvmInline
value class FullName private constructor(val value: String) {
    companion object {
        private val REGEX = Regex("^[A-Za-zÁÉÍÓÚÜÑáéíóúüñ\\s]{2,50}$")
        fun create(raw: String): FullName {
            val v = raw.trim()
            require(v.isNotEmpty() && REGEX.matches(v)) { "Nombre inválido" }
            return FullName(v)
        }
    }
    override fun toString() = value
}
