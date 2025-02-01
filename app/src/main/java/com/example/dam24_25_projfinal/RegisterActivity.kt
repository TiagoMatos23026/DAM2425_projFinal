package com.example.dam24_25_projfinal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dam24_25_projfinal.api.RetrofitInitializer
import com.example.dam24_25_projfinal.models.Utilizador
import com.example.dam24_25_projfinal.models.Utilizadore
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    private val bearerToken = "Bearer segredo"

    /**
     * Funcao chamada ao iniciar a atividade
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val usernameInput = findViewById<EditText>(R.id.username)
        val emailInput = findViewById<EditText>(R.id.email)
        val passwordInput = findViewById<EditText>(R.id.password)
        val registerButton = findViewById<Button>(R.id.register_button)
        val cancelButton = findViewById<Button>(R.id.cancel_button)

        cancelButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        registerButton.setOnClickListener {
            val username = usernameInput.text.toString().trim()
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()
            val biography = "Sem bio."

            if (username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                if (isValidEmail(email) && isValidPassword(password)) {
                    registerUser(username, email, password, biography)
                } else {
                    if (!isValidEmail(email) && !isValidPassword(password)) {
                        Toast.makeText(this, "Email inválido e senha insegura.", Toast.LENGTH_SHORT).show()
                    } else if (!isValidEmail(email)) {
                        Toast.makeText(this, "Email inválido.", Toast.LENGTH_SHORT).show()
                    } else if (!isValidPassword(password)) {
                        Toast.makeText(this, "Senha deve ter pelo menos 8 caracteres, incluir números e caracteres especiais.", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * Funcao para verificar se a password e aceitavel
     */
    private fun isValidPassword(password: String): Boolean {
        val regex = "^(?=.*[0-9])(?=.*[!@#\$%^&*(),.?\":{}|<>]).{8,}$".toRegex()
        return regex.matches(password)
    }

    /**
     * Funcao para verificar se o email e valido
     */
    private fun isValidEmail(email: String): Boolean {
        return email.contains("@")
    }

    /**
     * Funcao para registar um novo utilizador
     * Recebe um username, um email, uma password e uma biografia placeholder
     */
    private fun registerUser(username: String, email: String, password: String, biography: String) {
        val newUser = Utilizador(username, email, password, "2, 4", biography, null)
        val requestBody = Utilizadore(newUser)

        val call = RetrofitInitializer().ApiConnections().registerUser(bearerToken, requestBody)

        call.enqueue(object : Callback<Utilizadore> {
            override fun onResponse(call: Call<Utilizadore>, response: Response<Utilizadore>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@RegisterActivity, "Registo bem-sucedido!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this@RegisterActivity, "Erro no registo!", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Utilizadore>, t: Throwable) {
                Log.e("RegisterActivity", "Erro na API: ${t.message}")
                Toast.makeText(this@RegisterActivity, "Erro de conexão!", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
