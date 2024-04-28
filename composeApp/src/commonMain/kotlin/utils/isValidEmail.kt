package utils

fun isValidEmail(email: String): Boolean {
    val emailRegex = Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
    println("Test: validation ${email.matches(emailRegex)}")
    return email.matches(emailRegex)
}