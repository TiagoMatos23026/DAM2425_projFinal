package com.example.dam24_25_projfinal

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.dam24_25_projfinal.utils.Preferences


class PrivacyPolicyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy_policy)

        // Botão para aceitar os termos da Política de Privacidade

        val accept_privacy_policy_button = findViewById<Button>(R.id.accept_privacy_policy_button)
        accept_privacy_policy_button.setOnClickListener {
            //Marca os termos como aceites
            Preferences.setTermsAccepted(this, true)

            // Redireciona para a MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Encerra a atividade PrivacyPolicyAgreement
        }


    }
}