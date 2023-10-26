package com.meu.eletriccarapp.ui.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources.Theme
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat.ThemeCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.meu.eletriccarapp.R
import com.meu.eletriccarapp.data.CarsAPI
import com.meu.eletriccarapp.domain.Car
import com.meu.eletriccarapp.ui.adapter.CarAdapter
import org.json.JSONArray
import org.json.JSONTokener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection
import java.net.URL

class CarFragment : Fragment() {

    lateinit var listaCarros: RecyclerView
    lateinit var fabCalculate: FloatingActionButton
    lateinit var progressBar: ProgressBar
    lateinit var txt_no_wifi:TextView
    lateinit var iv_no_wifi:ImageView
    lateinit var carsAPI: CarsAPI
    private val urlBase = "https://igorbag.github.io/cars-api/"

    companion object{
        val carsArray = ArrayList<Car>()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.cars_fragment, container, false)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRetrofit()
        setupView(view)
        setListenerClick()
    }

    override fun onResume() {
        super.onResume()
        //If it's have internet, call service, else, return a message
        if (checkForInternet(context)){
            getAllCars()
            listaCarros.visibility = View.VISIBLE
            txt_no_wifi.visibility = View.GONE
            iv_no_wifi.visibility = View.GONE
        } else {
            emptyState()
        }
    }

    fun setupRetrofit(){
        val retrofit = Retrofit.Builder()
            .baseUrl(urlBase)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        carsAPI = retrofit.create(CarsAPI::class.java)
    }

    fun getAllCars(){
        carsAPI.getAllCars().enqueue(object : Callback<List<Car>>{
            @SuppressLint("UseCompatLoadingForDrawables")
            override fun onResponse(call: Call<List<Car>>, response: Response<List<Car>>) {
                if (response.isSuccessful){
                    response.body()?.let {
                        setupList(it)
                    }
                    progressBar.visibility = View.GONE
                    txt_no_wifi.visibility = View.GONE
                    iv_no_wifi.visibility = View.GONE
                } else {
                    onFailure(call, Throwable())
                }
            }

            override fun onFailure(call: Call<List<Car>>, t: Throwable) {
                Glide.with(iv_no_wifi.context).load(R.drawable.ic_error).into(iv_no_wifi)
                iv_no_wifi.visibility = View.VISIBLE
                txt_no_wifi.text = resources.getText(R.string.no_load_list)
                txt_no_wifi.visibility = View.VISIBLE
            }
        })
    }

    private fun emptyState() {
        progressBar.visibility = View.GONE
        listaCarros.visibility = View.GONE
        txt_no_wifi.visibility = View.VISIBLE
        iv_no_wifi.visibility = View.VISIBLE
        carsArray.clear()
    }

    fun setupView(view: View) {
        view.apply {
            listaCarros = findViewById(R.id.rv_lista_carros)
            fabCalculate = findViewById(R.id.fab_calcular)
            progressBar = findViewById(R.id.progress_circular)
            txt_no_wifi = findViewById(R.id.tv_no_wifi)
            iv_no_wifi = findViewById(R.id.iv_empty_state)
        }
    }

    fun setListenerClick() {
        fabCalculate.setOnClickListener {

        }
    }

    fun setupList(list: List<Car>) {
        val carAdapter = CarAdapter(list as ArrayList<Car>)
        listaCarros.apply {
            visibility = View.VISIBLE
            adapter = carAdapter
        }
    }

    private fun checkForInternet(context: Context?): Boolean {
        val connectivityManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            @Suppress("DEPRECATION")
            val networkInfo = connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }
}