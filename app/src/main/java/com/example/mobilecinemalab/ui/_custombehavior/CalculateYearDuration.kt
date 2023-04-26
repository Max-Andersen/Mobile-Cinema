package com.example.mobilecinemalab.ui._custombehavior

import com.example.mobilecinemalab.datasource.network.models.Episode

fun getYearDurationOfMovie(episodes: List<Episode>): String {
    val list = mutableListOf<Int>()
    episodes.forEach { list.add(it.year) }
    list.sort()
    return if (list.first() != list.last()) "${list.first()} - ${list.last()}" else "${list.first()}"
}