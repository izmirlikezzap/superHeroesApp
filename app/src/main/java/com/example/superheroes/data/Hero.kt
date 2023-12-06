package com.example.superheroes.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.superheroes.R



data class Hero(
    @StringRes val name: Int,
    @StringRes val description: Int,
    @DrawableRes val image: Int,
    @StringRes val spec: Int
)

val heroes = listOf(
    Hero(
        name = R.string.hero1,
        description = R.string.description1,
        image = R.drawable.android_superhero1,
        spec = R.string.extra1
    ),
    Hero(
        name = R.string.hero2,
        description = R.string.description2,
        image = R.drawable.android_superhero2,
        spec = R.string.extra2
    ),
    Hero(
        name = R.string.hero3,
        description = R.string.description3,
        image = R.drawable.android_superhero3,
        spec = R.string.extra3
    ),
    Hero(
        name = R.string.hero4,
        description = R.string.description4,
        image = R.drawable.android_superhero4,
        spec = R.string.extra4
    ),
    Hero(
        name = R.string.hero5,
        description = R.string.description5,
        image = R.drawable.android_superhero5,
        spec = R.string.extra5
    ),
    Hero(
        name = R.string.hero6,
        description = R.string.description6,
        image = R.drawable.android_superhero6,
        spec = R.string.extra6
    )
)
