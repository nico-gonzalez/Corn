package com.ng.tvshowsdb.shows.fixtures

import com.ng.tvshowsdb.shows.domain.model.TvShow

fun buildTvShow() = TvShow(1, "La casa de papel",
    "Money Heist", "", "", "", 8.9)

fun buildTvShows() = listOf(
    buildTvShow(),
    TvShow(2, "Friends",
        "Amigos", "", "", "", 8.0),
    TvShow(3, "Game Of Thrones",
        "GoT", "", "", "", 9.0),
    TvShow(4, "Homeland",
        "CasaTierra", "", "", "", 7.0),
    TvShow(5, "Mr. Robot",
        "Señor Robot", "", "", "", 8.5),
    TvShow(6, "Las chicas del cable",
        "Cable Girls", "", "", "", 5.5),
    TvShow(7, "How I met your mother",
        "Como conoci a tu madre", "", "", "", 4.5),
    TvShow(8, "Marco Polo",
        "Marco Polo", "", "", "", 8.5),
    TvShow(9, "Ozark",
        "Ozark", "", "", "", 6.5),
    TvShow(10, "House of cards",
        "Casa de cartas", "", "", "", 7.5)
)
