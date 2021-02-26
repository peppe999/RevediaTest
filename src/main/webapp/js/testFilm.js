function Movie(title, length, description, link, genres) {
    this.title = title;
    this.length = length;
    this.link = link;
    this.description = description;
    this.genres = genres;
}

function findMovie(movieId) {
    var ind = -1;
    for(var i = 0; i < loadedMoviesId.length; i++) {
        if(loadedMoviesId[i] == movieId)
            ind = i;
    }
    return ind;
}

var apiKey = "5bc6960951ac17218162630547dafb54";

var genres = [];
var loadedMoviesId = [];

var uploadImage = function (id, url) {
    $.ajax({
        url: "loaderFilm/upfilm/img",
        method: "POST",
        async: false,
        data: {
            id: id,
            url: url
        },
        success: function (response) {
            if (response === null)
                return;
        },
        fail: function (jqXHR, textStatus) {
            alert("Request failed: " + textStatus);
        }
    });
}

var uploadMovie = function (movieR, lnk, movGenres) {
    var movie = new Movie(movieR.title, movieR.runtime, movieR.overview, lnk, movGenres);
    $.ajax({
        url: "loaderFilm/upfilm",
        method: "POST",
        async: false,
        contentType: "application/json",
        data: JSON.stringify(movie),
        success: function (id) {
            if (id == -1)
                return;

            var img = (movieR.poster_path != null) ? movieR.poster_path : movieR.backdrop_path;
            uploadImage(id, "http://image.tmdb.org/t/p/w780" + img);
        },
        fail: function (jqXHR, textStatus) {
            alert("Request failed: " + textStatus);
        }
    });
}

var buildMovie = function (movieR) {
    $.ajax({
        url: "https://api.themoviedb.org/3/movie/" + movieR.id + "/videos",
        method: "GET",
        async: false,
        contentType: "application/json",
        data: {
            api_key: apiKey,
            language: "it-IT"
        },
        success: function (videos) {
            var lnk = null;
            for(var i = 0; i < videos.results.length; i++) {
                if(videos.results[i].site == "YouTube") {
                    lnk = "https://www.youtube.com/watch?v=" + videos.results[i].key;
                    break;
                }
            }

            var movGenres = movieR.genres.map(item => {return item.name})

            uploadMovie(movieR, lnk, movGenres);
        },
        fail: function (jqXHR, textStatus) {
            alert("Request failed: " + textStatus);
        }
    });
}

var loadMovieInfo = function (movieSum) {
    $.ajax({
        url: "https://api.themoviedb.org/3/movie/" + movieSum.id,
        method: "GET",
        async: false,
        contentType: "application/json",
        data: {
            api_key: apiKey,
            language: "it-IT"
        },
        success: function (movie) {
            if(movie.poster_path == null && movie.backdrop_path == null)
                return;

            buildMovie(movie);
        },
        fail: function (jqXHR, textStatus) {
            alert("Request failed: " + textStatus);
        }
    });
}

var loadMovies = function (genreId) {
    for (var i = 1; i <= 5; i++) {
        $.ajax({
            url: "https://api.themoviedb.org/3/discover/movie",
            method: "GET",
            async: false,
            contentType: "application/json",
            data: {
                api_key: apiKey,
                language: "it-IT",
                sort_by: "popularity.desc",
                include_adult: false,
                include_video: false,
                page: i,
                with_genres: genreId
            },
            success: function (response) {
                var respMovies = response.results;
                for (var i = 0; i < respMovies.length; i++) {
                    var ind = findMovie(respMovies[i].id);

                    if (ind != -1)
                        loadedMoviesId.push(respMovies[i].id);
                        loadMovieInfo(respMovies[i]);
                }
            },
            fail: function (jqXHR, textStatus) {
                alert("Request failed: " + textStatus);
            }
        });
    }
}

var uploadGenres = function () {
    $.ajax({
        url: "loaderFilm/upfilm/genres",
        method: "POST",
        async: false,
        contentType: "application/json",
        data: JSON.stringify(genres.map(item => {return item.name})),
        success: function (response) {
            if (response === null)
                return;
        },
        fail: function (jqXHR, textStatus) {
            alert("Request failed: " + textStatus);
        }
    });
}

var loadGenres = function() {
    $.ajax({
        url: "https://api.themoviedb.org/3/genre/movie/list",
        method: "GET",
        contentType: "application/json",
        data: {
          api_key: apiKey,
          language: "it-IT"
        },
        success: function(genresResp){
            genres = genresResp.genres;
            genres[15].name = "Film TV";
            uploadGenres();
            for(var i = 0; i < genres.length; i++) {
                loadMovies(genres[i].id);
                document.body.innerHTML += genres[i].name + "<br/>";
            }
        },
        fail: function( jqXHR, textStatus ) {
            alert( "Request failed: " + textStatus );
        }
    });
}

window.addEventListener("load", function () {
    //loadGenres();
});


/*$.ajax({
        url: "https://api.spotify.com/v1/recommendations/available-genre-seeds",
        method: "GET",
        contentType: "application/json",
        headers: {Authorization: "Bearer " + authId},
        success: function(genreObj){
            alert(genreObj.genres[0]);
            genres = genreObj.genres;
            for(var i = 0; i < genres.length; i++) {
                genres[i] = genres[i].replace(/-/g, " ");
                genres[i] = genres[i].charAt(0).toUpperCase() + genres[i].substr(1);
                document.body.innerHTML += genres[i] + "<br/>";
            }
            $.ajax({
                url: "loader/genres",
                method: "POST",
                data: JSON.stringify(genres),
                contentType: "application/json",
                success: function(){
                    alert("oki");
                },
                fail: function( jqXHR, textStatus ) {
                    alert( "Request failed: " + textStatus );
                }
            });
        },
        fail: function( jqXHR, textStatus ) {
            alert( "Request failed: " + textStatus );
        }
    });*/