package com.meu.eletriccarapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.meu.eletriccarapp.R
import com.meu.eletriccarapp.databinding.ItemCarBinding
import com.meu.eletriccarapp.domain.Car

class CarAdapter(private val carros: ArrayList<Car>, private val isFavoriteScreen: Boolean = false) :
    RecyclerView.Adapter<CarAdapter.ViewHolder>() {

    var carItemLister: (Car) -> Unit = {}

    inner class ViewHolder(private val binding: ItemCarBinding) : RecyclerView.ViewHolder(binding.root) {
        fun find(item:Car){
            with(itemView){
                binding.tvPrecoValue.text = item.preco
                binding.tvBateriaValue.text = item.bateria
                binding.tvPotenciaValue.text = item.potencia
                binding.tvRecargaValue.text = item.recarga
                Glide.with(context).load(item.urlPhoto).into(binding.ivImage)
            }
        }
        val viewFavorite = binding.ivFavorite
    }

    // Cria uma nova view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = ItemCarBinding.inflate(inflater, parent, false)
        return ViewHolder(view)
    }

    // Pega a quantidade de carros da lista
    override fun getItemCount() = carros.size

    // Pega o conteudo da view e troca pela informacao de item de uma lista
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.find(carros[position])

        if (isFavoriteScreen) {
            holder.viewFavorite.setImageResource(R.drawable.ic_favorite_selected)
        }

        holder.viewFavorite.setOnClickListener {
            val carro = carros[position]
            carItemLister(carro)
            setupFavorite(carro, holder)
        }
    }

    private fun setupFavorite(
        carro: Car,
        holder: ViewHolder
    ) {
        carro.isFavorite = !carro.isFavorite
        if (carro.isFavorite)
            holder.viewFavorite.setImageResource(R.drawable.ic_favorite_selected)
        else
            holder.viewFavorite.setImageResource(R.drawable.ic_favorite)
    }

}
