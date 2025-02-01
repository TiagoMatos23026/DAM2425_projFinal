package com.example.dam24_25_projfinal

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.dam24_25_projfinal.utils.Preferences


class PrivacyPolicyActivity : AppCompatActivity() {

    /**
     * Funcao chamada ao iniciar a atividade
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy_policy)

        val accept_privacy_policy_button = findViewById<Button>(R.id.accept_privacy_policy_button)
        accept_privacy_policy_button.setOnClickListener {

            Preferences.setTermsAccepted(this, true)

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }


    }
}