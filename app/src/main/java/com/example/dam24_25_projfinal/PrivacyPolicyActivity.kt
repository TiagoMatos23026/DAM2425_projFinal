package com.example.dam24_25_projfinal

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

/**
 * Fragmento responsável por apresentar o acordo de Política de Privacidade ao utilizador.
 */
class PrivacyPolicyAgreement : AppCompatActivity() {

    /**
     * Método chamado durante a criação da atividade.
     * @param savedInstanceState Estado previamente salvo da atividade, se disponível.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy_policy)

        // Botão para aceitar os termos da Política de Privacidade
        /*
        val accept_privacy_policy_button = findViewById<Button>(R.id.accept_privacy_policy_button)
        accept_privacy_policy_button.setOnClickListener {
            //Marca os termos como aceites
            SharedPreferencesHelper.setHasAcceptedTerms(this, true)

            // Redireciona para a MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Encerra a atividade PrivacyPolicyAgreement
        }
        */

    }
}