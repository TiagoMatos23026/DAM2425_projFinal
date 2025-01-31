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
import com.example.dam24_25_projfinal.models.UtilizadoresResponse
import com.example.dam24_25_projfinal.utils.Preferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val usernameInput = findViewById<EditText>(R.id.username)
        val passwordInput = findViewById<EditText>(R.id.password)
        val loginButton = findViewById<Button>(R.id.login_button)

        loginButton.setOnClickListener {
            val username = usernameInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                getUsers(username, password)
            } else {
                Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
            }
        }
        usernameInput.requestFocus()
    }

    private fun getUsers(username: String, password: String) {
        val retrofitData = RetrofitInitializer().ApiConnections().getAllUsers4Login()

        retrofitData.enqueue(object : Callback<UtilizadoresResponse?> {
            override fun onResponse(call: Call<UtilizadoresResponse?>, response: Response<UtilizadoresResponse?>) {
                val responseBody = response.body()

                if (responseBody != null && responseBody.utilizadores.isNotEmpty()) {
                    val user = responseBody.utilizadores.find { it.user == username && it.password == password }

                    if (user != null) {
                        Toast.makeText(this@LoginActivity, "Login bem-sucedido!", Toast.LENGTH_SHORT).show()
                        Preferences.saveToken(this@LoginActivity, "segredo")
                        Preferences.setUser(this@LoginActivity, user.id.toString())
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this@LoginActivity, "Credenciais inválidas!", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@LoginActivity, "Erro ao obter utilizadores!", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<UtilizadoresResponse?>, t: Throwable) {
                Log.e("LoginActivity", "Erro na API: ${t.message}")
                Toast.makeText(this@LoginActivity, "Erro de conexão com o servidor!", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
