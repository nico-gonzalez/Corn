package com.ng.tvshowsdb.movies.domain.errors

class MovieNotFound(val movieId: Long) : Throwable()
