@file:Suppress("UNUSED_VARIABLE", "unused")

package game.ceelo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import game.ceelo.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityLoginBinding.inflate(layoutInflater).apply {
            setContentView(root)
            login.setOnClickListener {
              val authService = null
                val authResultIsOk = securityService.login(
                    username.text.toString(),
                    password.text.toString()
                )
                if (authResultIsOk) finish()
            }
        }
    }
}

interface ISecurityService {
    fun login(username: String, password: String): Boolean
}

class SecurityService : ISecurityService {
    override fun login(username: String, password: String): Boolean {
        return true
    }
}

val securityService: ISecurityService = SecurityService()

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class Result<out T : Any> {

    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
        }
    }
}

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class LoggedInUser(
    val userId: String,
    val displayName: String
)