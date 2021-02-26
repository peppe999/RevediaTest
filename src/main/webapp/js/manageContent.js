var categoryTabContainer = document.getElementById("category-tab");
var musicChoiceTabContainer = document.getElementById("music-choice-tab");
var musicModalityTabContainer = document.getElementById("music-modality-tab");
var movieModalityTabContainer = document.getElementById("movie-modality-tab");
var bookModalityTabContainer = document.getElementById("book-modality-tab");
var apiSearchModalityTabContainer = document.getElementById("api-search-modality-tab");
var genreChoiceTabContainer = document.getElementById("genre-choice-tab");
var albumTabContainer = document.getElementById("album-tab");
var songTabContainer = document.getElementById("song-tab");
var movieTabContainer = document.getElementById("movie-tab");
var bookTabContainer = document.getElementById("book-tab");
var musicRadioBtn = document.getElementById("music-radio");
var movieRadioBtn = document.getElementById("film-radio");
var bookRadioBtn = document.getElementById("book-radio");
var albumRadioBtn = document.getElementById("album-radio");
var songRadioBtn = document.getElementById("song-radio");
var spotifyModeRadioBtn = document.getElementById("spotify-mode-radio");
var tmdbModeRadioBtn = document.getElementById("tmdb-mode-radio");
var gbooksModeRadioBtn = document.getElementById("gbooks-mode-radio");
var musicManualModeRadioBtn = document.getElementById("music-manual-mode-radio");
var movieManualModeRadioBtn = document.getElementById("movie-manual-mode-radio");
var bookManualModeRadioBtn = document.getElementById("book-manual-mode-radio");
var newContApiSearchBox = document.getElementById("newcont-api-search-box");
var albumNameInput = document.getElementById("album-name-input");
var albumLabelInput = document.getElementById("album-label-input");
var albumArtistInput = document.getElementById("album-artist-input");
var albumReleaseDateInput = document.getElementById("album-release-date-input");
var albumCoverFileInput = document.getElementById("album-cover-file-input");
var songNameInput = document.getElementById("song-name-input");
var songLengthInput = document.getElementById("song-length-input");
var songDescriptionInput = document.getElementById("song-description-input");
var movieNameInput = document.getElementById("movie-name-input");
var movieLengthInput = document.getElementById("movie-length-input");
var movieDescriptionInput = document.getElementById("movie-description-input");
var movieCoverFileInput = document.getElementById("movie-cover-file-input");
var bookNameInput = document.getElementById("book-name-input");
var bookPageNumInput = document.getElementById("book-page-num-input");
var bookPublisherInput = document.getElementById("book-publisher-input");
var bookAuthorInput = document.getElementById("book-author-input");
var bookDescriptionInput = document.getElementById("book-description-input");
var bookCoverFileInput = document.getElementById("book-cover-file-input");
var manageNextBtn = document.getElementById("manage-next-btn");
var manageBackBtn = document.getElementById("manage-back-btn");


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
var currentModeType = "";
var currentGenreList = [];





var renderGenres = function (loadedGenres) {
    var genreForm = genreChoiceTabContainer.getElementsByTagName("form")[0];
    var htmlContent = "";
    for(let i = 0; i < loadedGenres.length; i++) {
        htmlContent += '<div class="form-group d-flex align-items-center add-category-group">' +
            '<input type="checkbox" id="genre-checkbox-' + i + '" class="radio-toggle-x" name="genre-choice-checkbox">' +
                '<label class="d-flex align-items-center radio-label-x radio-label-x-sm" for="genre-checkbox-' + i + '">' + loadedGenres[i] + '</label>' +
        '</div>';
    }
    genreForm.innerHTML = htmlContent;
}

var loadAvailableGenres = function () {
    $.ajax({
        url: "/manage/getgenres",
        method: "POST",
        data: {
            type: currentContentType
        },
        success: function (response) {
            renderGenres(response);
        },
        fail: function (jqXHR, textStatus) {
            alert("Request failed: " + textStatus);
        }
    });
}






var setContentType = function () {
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
        currentWindow = movieModalityTabContainer;
        currentWindow.style.display = "initial";
    }
    else if(bookRadioBtn.checked) {
        manageBackBtn.innerText = "Indietro";
        currentContentType = "book";
        currentWindow.style.display = "none";
        currentWindow = bookModalityTabContainer;
        currentWindow.style.display = "initial";
    }
}

var resetContentType = function () {
    manageBackBtn.innerText = "Annulla";
    currentContentType = "";
    musicRadioBtn.checked = false;
    movieRadioBtn.checked = false;
    bookRadioBtn.checked = false;

    currentWindow.style.display = "none";
    currentWindow = categoryTabContainer;
    currentWindow.style.display = "initial";
}

var setMusicContentType = function () {
    if(albumRadioBtn.checked) {
        currentMusicContentType = "album";
        currentWindow.style.display = "none";
        currentWindow = musicModalityTabContainer;
        currentWindow.style.display = "initial";
    }
    else if(songRadioBtn.checked) {
        currentMusicContentType = "song";
        currentWindow.style.display = "none";
        currentWindow = musicModalityTabContainer;
        currentWindow.style.display = "initial";
    }
}

var resetMusicContentType = function () {
    currentMusicContentType = "";
    albumRadioBtn.checked = false;
    songRadioBtn.checked = false;
    currentWindow.style.display = "none";
    currentWindow = musicChoiceTabContainer;
    currentWindow.style.display = "initial";
}

var setModeType = function () {
    if(spotifyModeRadioBtn.checked || tmdbModeRadioBtn.checked || gbooksModeRadioBtn.checked) {
        currentModeType = "api";
        currentWindow.style.display = "none";
        currentWindow = apiSearchModalityTabContainer;
        currentWindow.style.display = "initial";
    }
    else if(musicManualModeRadioBtn.checked || movieManualModeRadioBtn.checked || bookManualModeRadioBtn.checked) {
        currentModeType = "manual";
        currentWindow.style.display = "none";
        if(currentMusicContentType === "song")
            currentWindow = songTabContainer;
        else {
            currentWindow = genreChoiceTabContainer;
            loadAvailableGenres();
        }
        currentWindow.style.display = "initial";
    }
}

var resetModeType = function () {
    var resultsForm = apiSearchModalityTabContainer.getElementsByTagName("form")[0];
    resultsForm.innerHTML = "";
    newContApiSearchBox.value = "";
    currentModeType = "";
    spotifyModeRadioBtn.checked = false;
    tmdbModeRadioBtn.checked = false;
    gbooksModeRadioBtn.checked = false;
    musicManualModeRadioBtn.checked = false;
    movieManualModeRadioBtn.checked = false;
    bookManualModeRadioBtn.checked = false;
    currentWindow.style.display = "none";
    if(currentContentType === "music")
        currentWindow = musicModalityTabContainer;
    else if(currentContentType === "movie")
        currentWindow = movieModalityTabContainer;
    else if(currentContentType === "book")
        currentWindow = bookModalityTabContainer;

    currentWindow.style.display = "initial";
}

var setGenreList = function () {
    var loadedGenres = genreChoiceTabContainer.getElementsByClassName("add-category-group");

    for(let i = 0; i < loadedGenres.length; i++) {
        if(loadedGenres[i].getElementsByTagName("input")[0].checked)
            currentGenreList.push(loadedGenres[i].getElementsByTagName("label")[0].innerText);
    }

    if(currentGenreList.length > 0) {
        currentWindow.style.display = "none";
        if(currentContentType === "music" && currentMusicContentType === "album") {
            currentWindow = albumTabContainer;
            manageNextBtn.innerText = "Aggiungi";
        }
        else if(currentContentType === "movie") {
            currentWindow = movieTabContainer;
            manageNextBtn.innerText = "Aggiungi";
        }
        else if(currentContentType === "book") {
            currentWindow = bookTabContainer;
            manageNextBtn.innerText = "Aggiungi";
        }

        currentWindow.style.display = "initial";
    }
}

var resetGenreList = function () {
    currentGenreList = [];
    var genreForm = genreChoiceTabContainer.getElementsByTagName("form")[0];
    genreForm.innerHTML = "";

    currentWindow.style.display = "none";
    currentWindow = genreChoiceTabContainer;
    currentWindow.style.display = "initial";
    loadAvailableGenres();
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
    else if(currentWindow === musicChoiceTabContainer || currentWindow === movieModalityTabContainer || currentWindow === bookModalityTabContainer)
        resetContentType();
    else if(currentWindow === musicModalityTabContainer)
        resetMusicContentType();
    else if(currentWindow === apiSearchModalityTabContainer || currentWindow === songTabContainer || currentWindow === genreChoiceTabContainer)
        resetModeType();
    else if(currentWindow === albumTabContainer || currentWindow === movieTabContainer || currentWindow === bookTabContainer)
        resetGenreList();
};

var goNextAction = function () {
    if(currentWindow === categoryTabContainer)
        setContentType();
    else if(currentWindow === musicChoiceTabContainer)
        setMusicContentType();
    else if(currentWindow === musicModalityTabContainer || currentWindow === movieModalityTabContainer || currentWindow === bookModalityTabContainer)
        setModeType();
    else if(currentWindow === genreChoiceTabContainer) {
        setGenreList();
    }
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
            if (response == null)
                return;

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
            if (response == null)
                return;

            if(response < 0 && trackID == null)
                return;

            if(response > 0)
                uploadSpotifyImage(response, imageURL);

            var songs = [];
            for(var i = 0; i < tracks.length; i++) {
                if(response > 0 || (response < 0 && tracks[i].id === trackID))
                    songs.push(new Song(tracks[i].name, response, tracks[i].duration_ms, tracks[i].external_urls.spotify, "Questo brano è stato generato automaticamente dal sistema"))
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
            if (id == -1)
                return;

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
            console.log("load image");
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
            if(id == -1)
                return;

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