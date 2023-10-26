package com.meu.eletriccarapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.meu.eletriccarapp.R
import com.meu.eletriccarapp.data.CarFactory
import com.meu.eletriccarapp.databinding.CarsFragmentBinding
import com.meu.eletriccarapp.databinding.FavoriteFragmentBinding
import com.meu.eletriccarapp.domain.Car
import com.meu.eletriccarapp.ui.adapter.CarAdapter
import com.meu.eletriccarapp.ui.adapter.TabAdapter

class FavoriteFragment(): Fragment() {

    lateinit var listaCarrosFavoritos: RecyclerView
    private val binding by lazy { FavoriteFragmentBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.favorite_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewFavorite(view)
    }

    private fun setupViewFavorite(view: View) {
        view.apply{
            listaCarrosFavoritos = findViewById(R.id.rv_favorite)
        }
    }
}