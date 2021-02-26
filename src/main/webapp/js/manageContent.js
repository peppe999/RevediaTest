var categoryTabContainer = document.getElementById("category-tab");
var musicChoiceTabContainer = document.getElementById("music-choice-tab");
var apiSearchModalityTabContainer = document.getElementById("api-search-modality-tab");
var musicRadioBtn = document.getElementById("music-radio");
var movieRadioBtn = document.getElementById("film-radio");
var bookRadioBtn = document.getElementById("book-radio");
var albumRadioBtn = document.getElementById("album-radio");
var songRadioBtn = document.getElementById("song-radio");
var newContApiSearchBox = document.getElementById("newcont-api-search-box");
var manageNextBtn = document.getElementById("manage-next-btn");
var manageBackBtn = document.getElementById("manage-back-btn");
var manageModalErrorLbl = document.getElementById("manage-modal-error-lbl");
var manageModalCloseBtn = document.getElementById("manage-modal-close-btn");


function Album(name, releaseDate, label, artist, genre) {
    this.name = name;
    this.releaseDate = releaseDate;
    this.label = label;
    this.artist = artist;
    this.genre = genre;
}

function Song(name, album, length, link, description) {
    this.name = name;
    this.albumID = album;
    this.length = length;
    this.link = link;
    this.description = description;
}

function Movie(title, length, description, link, genres) {
    this.title = title;
    this.length = length;
    this.link = link;
    this.description = description;
    this.genres = genres;
}

function Book(title, numberOfPages, description, publishingHouse, artist, genres, link){
    this.title = title;
    this.numberOfPages = numberOfPages;
    this.description = description;
    this.publishingHouse = publishingHouse;
    this.artist = artist;
    this.genres = genres;
    this.link = link;
}

var currentContentType = "";
var currentWindow = categoryTabContainer;
var currentMusicContentType = "";


var setContentType = function () {
    manageModalErrorLbl.style.display = "none";
    apiSearchModalityTabContainer.getElementsByTagName("form")[0].innerHTML = "";
    newContApiSearchBox.value = "";
    if(musicRadioBtn.checked) {
        manageBackBtn.innerText = "Indietro";
        currentContentType = "music";
        currentWindow.style.display = "none";
        currentWindow = musicChoiceTabContainer;
        currentWindow.style.display = "initial";
    }
    else if(movieRadioBtn.checked) {
        manageBackBtn.innerText = "Indietro";
        currentContentType = "movie";
        currentWindow.style.display = "none";
        currentWindow = apiSearchModalityTabContainer;
        currentWindow.style.display = "initial";
        manageNextBtn.innerText = "Aggiungi";
    }
    else if(bookRadioBtn.checked) {
        manageBackBtn.innerText = "Indietro";
        currentContentType = "book";
        currentWindow.style.display = "none";
        currentWindow = apiSearchModalityTabContainer;
        currentWindow.style.display = "initial";
        manageNextBtn.innerText = "Aggiungi";
    }
}

var resetContentType = function () {
    manageModalErrorLbl.style.display = "none";
    apiSearchModalityTabContainer.getElementsByTagName("form")[0].innerHTML = "";
    newContApiSearchBox.value = "";
    manageBackBtn.innerText = "Annulla";
    currentContentType = "";
    musicRadioBtn.checked = false;
    movieRadioBtn.checked = false;
    bookRadioBtn.checked = false;

    currentWindow.style.display = "none";
    currentWindow = categoryTabContainer;
    currentWindow.style.display = "initial";
    manageNextBtn.innerText = "Avanti";
}

var setMusicContentType = function () {
    manageModalErrorLbl.style.display = "none";
    apiSearchModalityTabContainer.getElementsByTagName("form")[0].innerHTML = "";
    newContApiSearchBox.value = "";
    if(albumRadioBtn.checked) {
        currentMusicContentType = "album";
        currentWindow.style.display = "none";
        currentWindow = apiSearchModalityTabContainer;
        currentWindow.style.display = "initial";
        manageNextBtn.innerText = "Aggiungi";
    }
    else if(songRadioBtn.checked) {
        currentMusicContentType = "song";
        currentWindow.style.display = "none";
        currentWindow = apiSearchModalityTabContainer;
        currentWindow.style.display = "initial";
        manageNextBtn.innerText = "Aggiungi";
    }
}

var resetMusicContentType = function () {
    manageModalErrorLbl.style.display = "none";
    apiSearchModalityTabContainer.getElementsByTagName("form")[0].innerHTML = "";
    newContApiSearchBox.value = "";
    currentMusicContentType = "";
    albumRadioBtn.checked = false;
    songRadioBtn.checked = false;
    currentWindow.style.display = "none";
    currentWindow = musicChoiceTabContainer;
    currentWindow.style.display = "initial";
    manageNextBtn.innerText = "Avanti";
}

var startApiSearch = function () {
    if(currentContentType === "music")
        startSpotifySearch(newContApiSearchBox.value);
    else if(currentContentType === "movie")
        startTMDBSearch(newContApiSearchBox.value);
    else if(currentContentType === "book")
        startGBooksSearch(newContApiSearchBox.value);
}

var goBackAction = function () {
    if(currentWindow === categoryTabContainer)
        $("#addModal").modal("hide");
    else if(currentWindow === musicChoiceTabContainer || (currentWindow === apiSearchModalityTabContainer && currentContentType != "music"))
        resetContentType();
    else if(currentWindow === apiSearchModalityTabContainer && currentContentType === "music")
        resetMusicContentType();
};

var goNextAction = function () {
    if(currentWindow === categoryTabContainer)
        setContentType();
    else if(currentWindow === musicChoiceTabContainer)
        setMusicContentType();

    else if(currentWindow === apiSearchModalityTabContainer && currentContentType == "music")
        startUploadSpotify();
    else if(currentWindow === apiSearchModalityTabContainer && currentContentType == "movie")
        startUploadTMDB();
    else if(currentWindow === apiSearchModalityTabContainer && currentContentType == "book")
        startUploadGBooks();
};

manageBackBtn.addEventListener("click", function () {
    goBackAction();
});

manageBackBtn.addEventListener("keydown", function (event) {
    if(event.key === "Enter")
        goBackAction();
});

manageNextBtn.addEventListener("click", function () {
    goNextAction();
});

manageNextBtn.addEventListener("keydown", function (event) {
    if(event.key === "Enter")
        goNextAction();
});

newContApiSearchBox.addEventListener("keydown", function (event) {
    if(event.key === "Enter") {
        startApiSearch();
    }
});

newContApiSearchBox.addEventListener("focusout", function () {
    startApiSearch();
});

manageModalCloseBtn.addEventListener("click", function () {
   resetMusicContentType();
   resetContentType();
});

manageModalCloseBtn.addEventListener("keydown", function (event) {
    if(event.key === "Enter") {
        resetMusicContentType();
        resetContentType();
    }
});











// SPOTIFY

var clientId = "a58238798239437cbc0ac5a5701e5ce1";
var clientSecret = "edb7941f72874a43a66fbb03d3694d7f";
var spotifySearchResult;

var uploadSpotifySongs = function (songs) {

    $.ajax({
        url: "manage/upload/songs",
        method: "POST",
        contentType: "application/json",
        data: JSON.stringify(songs),
        success: function (response) {
            if (response == null)  {
                manageModalErrorLbl.style.display = "initial";
                return;
            }

            resetMusicContentType();
            resetContentType();

            $("#addModal").modal("hide");

        },
        fail: function (jqXHR, textStatus) {
            alert("Request failed: " + textStatus);
        }
    });
}

var uploadSpotifyImage = function (id, url) {
    $.ajax({
        url: "manage/upload/spotify/img",
        method: "POST",
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

var uploadSpotifyAlbum = function (alb, tracks, trackID, imageURL) {
    $.ajax({
        url: "manage/upload/album",
        method: "POST",
        contentType: "application/json",
        data: JSON.stringify(alb),
        success: function (response) {
            if (response == null)  {
                manageModalErrorLbl.style.display = "initial";
                return;
            }

            if(response < 0 && trackID == null) {
                manageModalErrorLbl.style.display = "initial";
                return;
            }

            if(response > 0)
                uploadSpotifyImage(response, imageURL);

            var songs = [];
            for(var i = 0; i < tracks.length; i++) {
                if(response > 0 || (response < 0 && tracks[i].id === trackID)) {
                    var albId = response;
                    if(albId < 0)
                        albId *= -1;
                    songs.push(new Song(tracks[i].name, albId, tracks[i].duration_ms, tracks[i].external_urls.spotify, "Questo brano è stato generato automaticamente dal sistema"))
                }
            }

            uploadSpotifySongs(songs);
        },
        fail: function (jqXHR, textStatus) {
            alert("Request failed: " + textStatus);
        }
    });
}

var loadSpotifyAlbumGenresAndSongs = function (authId, album, trackID) {
    $.ajax({
        url: "https://api.spotify.com/v1/artists/" + album.artists[0].id,
        method: "GET",
        contentType: "application/json",
        headers: {Authorization: "Bearer " + authId},
        success: function (response) {
            var genres = [];
            for(var i = 0; i < response.genres.length; i++)
                genres.push("" + response.genres[i]);
            var alb = new Album(album.name, album.release_date, album.label, response.name, genres);
            var tracks = album.tracks.items;

            uploadSpotifyAlbum(alb, tracks, trackID, album.images[0].url);
        },
        fail: function (jqXHR, textStatus) {
            alert("Request failed: " + textStatus);
        }
    });
}

var loadSpotifyAlbum = function (authId, shortAlbum, trackID) {
    $.ajax({
        url: "https://api.spotify.com/v1/albums/" + shortAlbum.id,
        method: "GET",
        contentType: "application/json",
        data: {
            market: "it"
        },
        headers: {Authorization: "Bearer " + authId},
        success: function (response) {
            loadSpotifyAlbumGenresAndSongs(authId, response, trackID);
        },
        fail: function (jqXHR, textStatus) {
            alert("Request failed: " + textStatus);
        }
    });
}

var getOtherInfoSpotify = function (index) {
    $.ajax({
        url: "https://accounts.spotify.com/api/token",
        method: "POST",
        contentType: "application/x-www-form-urlencoded",
        data: {grant_type : "client_credentials"},
        headers: {Authorization: "Basic " + btoa(clientId+":"+clientSecret)},
        success: function(auth){
            if(currentMusicContentType === "album") {
                var element = spotifySearchResult.albums.items[index];

                loadSpotifyAlbum(auth.access_token, element, null);
            }
            else if(currentMusicContentType === "song") {
                var element = spotifySearchResult.tracks.items[index].album;

                loadSpotifyAlbum(auth.access_token, element, spotifySearchResult.tracks.items[index].id);
            }
        },
        fail: function( jqXHR, textStatus ) {
            alert( "Request failed: " + textStatus );
        }
    });
}

var startUploadSpotify = function () {
    var loadedResults = apiSearchModalityTabContainer.getElementsByClassName("add-category-group");

    for(let i = 0; i < loadedResults.length; i++) {
        if(loadedResults[i].getElementsByTagName("input")[0].checked) {
            getOtherInfoSpotify(i);
            break;
        }
    }
}

var manageSpotifyResults = function () {
    var htmlContent = "";
    var resultsForm = apiSearchModalityTabContainer.getElementsByTagName("form")[0];
    if(currentMusicContentType === "album") {
        var searchList = spotifySearchResult.albums.items;
        for(let i = 0; i < searchList.length; i++) {
            htmlContent += '<div class="form-group d-flex align-items-center add-category-group">' +
                '<input type="radio" id="api-mode-result-radio-' + i + '" class="radio-toggle-x" name="api-mode-choice-radio">' +
                '<label class="d-flex align-items-center radio-label-x radio-label-x-sm" for="api-mode-result-radio-' + i + '"></label>' +
                '</div>';
        }
        resultsForm.innerHTML = htmlContent;
        for(let i = 0; i < searchList.length; i++) {
            resultsForm.getElementsByTagName("label")[i].innerText = searchList[i].name + " - " + searchList[i].artists[0].name;
        }
    }
    else if(currentMusicContentType === "song") {
        var searchList = spotifySearchResult.tracks.items;
        for(let i = 0; i < searchList.length && i < 5; i++) {
            htmlContent += '<div class="form-group d-flex align-items-center add-category-group">' +
                '<input type="radio" id="api-mode-result-radio-' + i + '" class="radio-toggle-x" name="api-mode-choice-radio">' +
                '<label class="d-flex align-items-center radio-label-x radio-label-x-sm" for="api-mode-result-radio-' + i + '"></label>' +
                '</div>';
        }
        resultsForm.innerHTML = htmlContent;
        for(let i = 0; i < searchList.length && i < 5; i++) {
            resultsForm.getElementsByTagName("label")[i].innerText = searchList[i].name + " - " + searchList[i].album.name + " - " + searchList[i].artists[0].name;
        }
    }
}

var spotifySearch = function (authId, myQuery) {
    var spotifyType = currentMusicContentType;
    if(currentMusicContentType === "song")
        spotifyType = "track";

    $.ajax({
        url: "https://api.spotify.com/v1/search",
        method: "GET",
        contentType: "application/json",
        data: {
            q: myQuery,
            type: spotifyType,
            market: "it",
            limit: "5"
        },
        headers: {Authorization: "Bearer " + authId},
        success: function (response) {
            spotifySearchResult = response;
            manageSpotifyResults();
        },
        fail: function (jqXHR, textStatus) {
            alert("Request failed: " + textStatus);
        }
    });
}

var startSpotifySearch = function (myQuery) {
    $.ajax({
        url: "https://accounts.spotify.com/api/token",
        method: "POST",
        contentType: "application/x-www-form-urlencoded",
        data: {grant_type : "client_credentials"},
        headers: {Authorization: "Basic " + btoa(clientId+":"+clientSecret)},
        success: function(auth){
            spotifySearch(auth.access_token, myQuery);
        },
        fail: function( jqXHR, textStatus ) {
            alert( "Request failed: " + textStatus );
        }
    });
}





// TMDB

var tmdbApiKey = "5bc6960951ac17218162630547dafb54";
var tmdbSearchResult;
var tmdbGenres = [];

var startUploadTMDB = function () {
    var loadedResults = apiSearchModalityTabContainer.getElementsByClassName("add-category-group");

    for(let i = 0; i < loadedResults.length; i++) {
        if(loadedResults[i].getElementsByTagName("input")[0].checked) {
            loadTMDBMovieInfo(tmdbSearchResult.results[i]);
            break;
        }
    }
}

var manageTMDBResults = function () {
    var htmlContent = "";
    var resultsForm = apiSearchModalityTabContainer.getElementsByTagName("form")[0];
    var searchList = tmdbSearchResult.results;
    for(let i = 0; i < searchList.length && i < 5; i++) {
        htmlContent += '<div class="form-group d-flex align-items-center add-category-group">' +
            '<input type="radio" id="api-mode-result-radio-' + i + '" class="radio-toggle-x" name="api-mode-choice-radio">' +
            '<label class="d-flex align-items-center radio-label-x radio-label-x-sm" for="api-mode-result-radio-' + i + '"></label>' +
            '</div>';
    }
    resultsForm.innerHTML = htmlContent;
    for(let i = 0; i < searchList.length && i < 5; i++) {
        resultsForm.getElementsByTagName("label")[i].innerText = searchList[i].title;
    }
}

var startTMDBSearch = function (myQuery) {
    $.ajax({
        url: "https://api.themoviedb.org/3/search/movie",
        method: "GET",
        contentType: "application/json",
        data: {
            api_key: tmdbApiKey,
            language: "it-IT",
            query: myQuery,
            include_adult: false
        },
        success: function (response) {
            tmdbSearchResult = response;
            manageTMDBResults();
        },
        fail: function (jqXHR, textStatus) {
            alert("Request failed: " + textStatus);
        }
    });
}

var loadTMDBGenres = function() {
    $.ajax({
        url: "https://api.themoviedb.org/3/genre/movie/list",
        method: "GET",
        contentType: "application/json",
        data: {
            api_key: tmdbApiKey,
            language: "it-IT"
        },
        success: function(genresResp){
            tmdbGenres = genresResp.genres;
            tmdbGenres[15].name = "Film TV";
        },
        fail: function( jqXHR, textStatus ) {
            alert( "Request failed: " + textStatus );
        }
    });
}

loadTMDBGenres();

var uploadTMDBImage = function (id, url) {
    $.ajax({
        url: "manage/upload/tmdb/img",
        method: "POST",
        data: {
            id: id,
            url: url
        },
        success: function (response) {
            if (response === null)
                return;

            resetMusicContentType();
            resetContentType();

            $("#addModal").modal("hide");
        },
        fail: function (jqXHR, textStatus) {
            alert("Request failed: " + textStatus);
        }
    });
}

var uploadTMDBMovie = function (movieR, lnk, movGenres) {
    var movie = new Movie(movieR.title, movieR.runtime, movieR.overview, lnk, movGenres);
    $.ajax({
        url: "manage/upload/movie",
        method: "POST",
        contentType: "application/json",
        data: JSON.stringify(movie),
        success: function (id) {
            if (id == -1) {
                manageModalErrorLbl.style.display = "initial";
                return;
            }

            var img = (movieR.poster_path != null) ? movieR.poster_path : movieR.backdrop_path;
            uploadTMDBImage(id, "http://image.tmdb.org/t/p/w780" + img);
        },
        fail: function (jqXHR, textStatus) {
            alert("Request failed: " + textStatus);
        }
    });
}

var buildTMDBMovie = function (movieR) {
    $.ajax({
        url: "https://api.themoviedb.org/3/movie/" + movieR.id + "/videos",
        method: "GET",
        contentType: "application/json",
        data: {
            api_key: tmdbApiKey,
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

            uploadTMDBMovie(movieR, lnk, movGenres);
        },
        fail: function (jqXHR, textStatus) {
            alert("Request failed: " + textStatus);
        }
    });
}

var loadTMDBMovieInfo = function (movieSum) {
    $.ajax({
        url: "https://api.themoviedb.org/3/movie/" + movieSum.id,
        method: "GET",
        contentType: "application/json",
        data: {
            api_key: tmdbApiKey,
            language: "it-IT"
        },
        success: function (movie) {
            if(movie.poster_path == null && movie.backdrop_path == null)
                return;

            buildTMDBMovie(movie);
        },
        fail: function (jqXHR, textStatus) {
            alert("Request failed: " + textStatus);
        }
    });
}





// GOOGLE BOOKS

var gBooksSearchResult;

var uploadImage = function(id, url){
    $.ajax({
        url:"manage/upload/gbooks/img",
        method:"POST",
        data: {
            id: id,
            url: url
        },
        success: function(response){
            if (response === null)
                return;

            resetMusicContentType();
            resetContentType();

            $("#addModal").modal("hide");
        },
    });

}


var uploadGBooksBook = function(rawBook){
    var book = new Book(rawBook.volumeInfo.title, rawBook.volumeInfo.pageCount, rawBook.volumeInfo.description, rawBook.volumeInfo.publisher, rawBook.volumeInfo.authors[0], rawBook.volumeInfo.categories, rawBook.volumeInfo.previewLink);
    $.ajax({
        url:"manage/upload/book",
        method: "POST",
        contentType: "application/JSON",
        data: JSON.stringify(book),
        success: function (id) {
            console.log("load success");
            if(id == -1) {
                manageModalErrorLbl.style.display = "initial";
                return;
            }

            uploadImage(id, rawBook.volumeInfo.imageLinks.thumbnail.replace(/zoom=1/g, "zoom=0"));
        }
    });
}

var loadGBooksBookInfo = function (book) {
    $.ajax({
        url: "https://www.googleapis.com/books/v1/volumes/" + book.id,
        method: "GET",
        contentType: "application/json",
        success: function (response) {
            uploadGBooksBook(response);
        },
        fail: function (jqXHR, textStatus) {
            alert("Request failed: " + textStatus);
        }
    });
}

var startUploadGBooks = function () {
    var loadedResults = apiSearchModalityTabContainer.getElementsByClassName("add-category-group");

    for(let i = 0; i < loadedResults.length; i++) {
        if(loadedResults[i].getElementsByTagName("input")[0].checked) {
            loadGBooksBookInfo(gBooksSearchResult.items[i]);
            break;
        }
    }
}

var manageGBooksResults = function () {
    var htmlContent = "";
    var resultsForm = apiSearchModalityTabContainer.getElementsByTagName("form")[0];
    var searchList = gBooksSearchResult.items;
    for(let i = 0; i < searchList.length && i < 5; i++) {
        htmlContent += '<div class="form-group d-flex align-items-center add-category-group">' +
            '<input type="radio" id="api-mode-result-radio-' + i + '" class="radio-toggle-x" name="api-mode-choice-radio">' +
            '<label class="d-flex align-items-center radio-label-x radio-label-x-sm" for="api-mode-result-radio-' + i + '"></label>' +
            '</div>';
    }
    resultsForm.innerHTML = htmlContent;
    for(let i = 0; i < searchList.length && i < 5; i++) {
        resultsForm.getElementsByTagName("label")[i].innerText = searchList[i].volumeInfo.title;
    }
}

var startGBooksSearch = function (myQuery) {
    $.ajax({
        url: "https://www.googleapis.com/books/v1/volumes",
        method: "GET",
        contentType: "application/json",
        data: {
            q: myQuery
        },
        success: function (response) {
            gBooksSearchResult = response;
            manageGBooksResults();
        },
        fail: function (jqXHR, textStatus) {
            alert("Request failed: " + textStatus);
        }
    });
}