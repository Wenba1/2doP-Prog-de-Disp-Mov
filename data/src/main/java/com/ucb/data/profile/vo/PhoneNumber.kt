package com.ucb.data.profile.vo

@JvmInline
value class PhoneNumber private constructor(val value: String) {
    companion object {
        private val REGEX = Regex("^\\+?\\d{7,15}$")
        fun create(raw: String): PhoneNumber {
            val v = raw.replace(" ", "")
            require(v.isNotEmpty() && REGEX.matches(v)) { "Teléfono inválido" }
            return PhoneNumber(v)
        }
    }
    override fun toString() = value
}
