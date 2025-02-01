package com.example.dam24_25_projfinal

import android.Manifest
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.dam24_25_projfinal.api.RetrofitInitializer
import com.example.dam24_25_projfinal.models.Pagina
import com.example.dam24_25_projfinal.models.Paginae
import com.example.dam24_25_projfinal.utils.Preferences
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationTokenSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreatePageFragment : Fragment() {

    private lateinit var tituloEditText: EditText
    private lateinit var textoEditText: EditText
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    /**
     * Funcao chamada ao criar a view para o fragmento
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_create_page, container, false)

        tituloEditText = view.findViewById(R.id.editTextTitulo)
        textoEditText = view.findViewById(R.id.editTextTexto)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) ||
                        permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    Toast.makeText(requireContext(), "Acesso à localização concedido.", Toast.LENGTH_SHORT).show()
                    getLocationAndCreatePage()
                }
                else -> {
                    Toast.makeText(requireContext(), "Permissão de localização negada.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        view.findViewById<View>(R.id.buttonCreatePage).setOnClickListener {
            locationPermissionRequest.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }

        return view
    }

    private fun getLocationAndCreatePage() {
        // Verifica se a permissão foi concedida explicitamente
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            if (isLocationEnabled()) {
                fusedLocationClient.getCurrentLocation(
                    com.google.android.gms.location.Priority.PRIORITY_BALANCED_POWER_ACCURACY,
                    CancellationTokenSource().token
                ).addOnCompleteListener { task ->
                    val location = task.result
                    if (location != null) {
                        val geocoder = Geocoder(requireContext())
                        val addressList = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                        val city = addressList?.firstOrNull()?.locality
                        val country = addressList?.firstOrNull()?.countryName

                        // Salvar cidade e país no parâmetro "localizacao" de Pagina
                        val cidadeEpais = "$city, $country"
                        createPage(cidadeEpais)
                    } else {
                        Toast.makeText(requireContext(), "Falha ao obter localização.", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Por favor, ative a localização.", Toast.LENGTH_SHORT).show()
            }
        } else {
            // Permissão não concedida, solicita permissão
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                1001
            )
        }
    }

    private fun createPage(localizacao: String) {
        val titulo = tituloEditText.text.toString()
        val texto = textoEditText.text.toString()

        if (TextUtils.isEmpty(titulo)) {
            Toast.makeText(requireContext(), "Título é obrigatório", Toast.LENGTH_SHORT).show()
            return
        }

        val userId = Preferences.getUser(requireContext())?.toIntOrNull()
        userId?.let {
            val token = Preferences.getToken(requireContext())
            val bearerToken = "Bearer $token"

            val pagina = Pagina(
                titulo = titulo,
                texto = texto,
                localizacao = localizacao,
                utilizador = it,
                id = null
            )

            val paginae = Paginae(pagina)

            val call = RetrofitInitializer().ApiConnections().createPage(bearerToken, paginae)
            call.enqueue(object : Callback<Paginae> {
                override fun onResponse(call: Call<Paginae>, response: Response<Paginae>) {
                    if (response.isSuccessful) {
                        Toast.makeText(requireContext(), "Página criada com sucesso!", Toast.LENGTH_SHORT).show()
                        parentFragmentManager.beginTransaction()
                            .replace(R.id.frame_layout, ProfileFragment())
                            .addToBackStack(null)
                            .commit()
                    } else {
                        Toast.makeText(requireContext(), "Erro ao criar página", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Paginae>, t: Throwable) {
                    Toast.makeText(requireContext(), "Falha na conexão: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager = requireContext().getSystemService(LOCATION_SERVICE) as android.location.LocationManager
        return locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)
    }
}
