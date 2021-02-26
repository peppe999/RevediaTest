const sleep = (s) => {
    return new Promise(resolve => setTimeout(resolve, (s*1000)))
}

function Album(name, releaseDate, label, artist, genres) {
    this.name = name;
    this.releaseDate = releaseDate;
    this.label = label;
    this.artist = artist;
    this.genre = genres;
}

function Song(name, album, length, link, description) {
    this.name = name;
    this.albumID = album;
    this.length = length;
    this.link = link;
    this.description = description;
}

function Artist(id, name) {
    this.id = id;
    this.name = name;
    this.genresList = [];
    this.albumList = [];
}

function findArtist(artist) {
    var ind = -1;
    for(var i = 0; i < artists.length; i++) {
        if(artists[i].id === artist.id)
            ind = i;
    }
    return ind;
}

var clientId = "a58238798239437cbc0ac5a5701e5ce1";
var clientSecret = "edb7941f72874a43a66fbb03d3694d7f";

var genres;
var artists = [];

var uploadSongs = function (albumid, tracks) {
    for (var i = 0; i < tracks.length; i++) {
        var song = new Song(tracks[i].name, albumid, tracks[i].duration_ms, tracks[i].external_urls.spotify, "Questo brano è stato generato automaticamente dal sistema");
        $.ajax({
            url: "loader/upsong",
            method: "POST",
            contentType: "application/json",
            data: JSON.stringify(song),
            success: function (response) {
                if (response == null)
                    return;
            },
            fail: function (jqXHR, textStatus) {
                alert("Request failed: " + textStatus);
            }
        });
    }
}

var uploadImage = function (id, url) {
    $.ajax({
        url: "loader/upalbum/img",
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

var loadAlbumInfo = function (authId, albumId, artist) {
    $.ajax({
        url: "https://api.spotify.com/v1/albums/" + albumId,
        method: "GET",
        contentType: "application/json",
        data: {
            market: "it"
        },
        headers: {Authorization: "Bearer " + authId},
        success: function (response) {
            var alb = new Album(response.name, response.release_date, response.label, artist.name, artist.genresList);
            $.ajax({
                url: "loader/upalbum",
                method: "POST",
                contentType: "application/json",
                async: false,
                data: JSON.stringify(alb),
                success: function (id) {
                    if (id == -1)
                        return;

                    uploadImage(id, response.images[0].url);
                    uploadSongs(id, response.tracks.items);
                },
                fail: function (jqXHR, textStatus) {
                    alert("Request failed: " + textStatus);
                }
            });
        },
        fail: function (jqXHR, textStatus) {
            alert("Request failed: " + textStatus);
        }
    });
}

var loadAlbums = function (authId, artist) {
    $.ajax({
        url: "https://api.spotify.com/v1/artists/" + artist.id + "/albums",
        method: "GET",
        contentType: "application/json",
        async: false,
        data: {
            market: "it",
            limit: "5"
        },
        headers: {Authorization: "Bearer " + authId},
        success: function (response) {
            var albumsList = response.items;
            for (var i = 0; i < albumsList.length; i++) {
                loadAlbumInfo(authId, albumsList[i].id, artist);
            }
        },
        fail: function (jqXHR, textStatus) {
            alert("Request failed: " + textStatus);
        }
    });
}

var loadArtists = function (authId) {
    for (var i = 0; i < genres.length; i++) {
        $.ajax({
            url: "https://api.spotify.com/v1/search",
            method: "GET",
            genre: genres[i],
            async: false,
            contentType: "application/json",
            data: {
                q: 'genre:"' + genres[i] + '"',
                type: "artist",
                market: "it",
                limit: "5"
            },
            headers: {Authorization: "Bearer " + authId},
            success: function (response) {
                var respArtists = response.artists.items;
                for (var i = 0; i < respArtists.length; i++) {
                    var art = new Artist(respArtists[i].id, respArtists[i].name);
                    var ind = findArtist(art);

                    if (ind == -1) {
                        art.genresList.push(this.genre);
                        artists.push(art);
                    } else
                        artists[ind].genresList.push(this.genre);
                }
            },
            fail: function (jqXHR, textStatus) {
                alert("Request failed: " + textStatus);
            }
        });
    }
    for (var i = 0; i < artists.length; i++) {
        document.body.innerHTML += artists[i].name + "<br/>";

        loadAlbums(authId, artists[i]);
    }
}

var loadGenres = function(authId) {
    $.ajax({
        url: "loader/genres",
        method: "GET",
        contentType: "application/json",
        success: function(genresResp){
            genres = genresResp;
            for(var i = 0; i < genres.length; i++) {
                document.body.innerHTML += genres[i] + "<br/>";
            }
            loadArtists(authId);
        },
        fail: function( jqXHR, textStatus ) {
            alert( "Request failed: " + textStatus );
        }
    });
}

window.addEventListener("load", function () {
    $.ajax({
        url: "https://accounts.spotify.com/api/token",
        method: "POST",
        contentType: "application/x-www-form-urlencoded",
        data: {grant_type : "client_credentials"},
        headers: {Authorization: "Basic " + btoa(clientId+":"+clientSecret)},
        success: function(auth){
            loadGenres(auth.access_token);
        },
        fail: function( jqXHR, textStatus ) {
            alert( "Request failed: " + textStatus );
        }
    });
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