package com.meu.eletriccarapp.data

import com.meu.eletriccarapp.domain.Car

object CarFactory {
    val list = listOf(
        Car(
            id = 1,
            preco = "R$ 300.000,00",
            bateria = "300 kWh",
            potencia = "200cv",
            recarga = "30 min",
            urlPhoto = "www.google.com.br",
            isFavorite = false,
        ),
        Car(
            id = 2,
            preco = "R$ 200.000,00",
            bateria = "200 kWh",
            potencia = "150cv",
            recarga = "40 min",
            urlPhoto = "www.google.com.br",
            isFavorite = false
        ),
        Car(
            id = 3,
            preco = "R$ 300.000,00",
            bateria = "200 kWh",
            potencia = "150cv",
            recarga = "40 min",
            urlPhoto = "www.google.com.br",
            isFavorite = false
        )
    )
}