var loadLoadedSongsNum = function () {
    $.ajax({
        url: "manage/songsnum",
        method: "POST",
        contentType: "application/json",
        success: function(songsNum){
            var content;
            if(songsNum == null)
                content = "---";
            else
                content = songsNum;

            document.getElementById("loaded-songs-num").innerText = content;
        },
        fail: function( jqXHR, textStatus ) {
            alert( "Request failed: " + textStatus );
        }
    });
}

var loadLoadedAlbumsNum = function () {
    $.ajax({
        url: "manage/albumsnum",
        method: "POST",
        contentType: "application/json",
        success: function(albumsNum){
            var content;
            if(albumsNum == null)
                content = "---";
            else
                content = albumsNum;

            document.getElementById("loaded-albums-num").innerText = content;
        },
        fail: function( jqXHR, textStatus ) {
            alert( "Request failed: " + textStatus );
        }
    });
}

var loadLoadedMoviesNum = function () {
    $.ajax({
        url: "manage/moviesnum",
        method: "POST",
        contentType: "application/json",
        success: function(moviesNum){
            var content;
            if(moviesNum == null)
                content = "---";
            else
                content = moviesNum;

            document.getElementById("loaded-movies-num").innerText = content;
        },
        fail: function( jqXHR, textStatus ) {
            alert( "Request failed: " + textStatus );
        }
    });
}

var loadLoadedBooksNum = function () {
    $.ajax({
        url: "manage/booksnum",
        method: "POST",
        contentType: "application/json",
        success: function(booksNum){
            var content;
            if(booksNum == null)
                content = "---";
            else
                content = booksNum;

            document.getElementById("loaded-books-num").innerText = content;
        },
        fail: function( jqXHR, textStatus ) {
            alert( "Request failed: " + textStatus );
        }
    });
}

loadLoadedAlbumsNum();
loadLoadedBooksNum();
loadLoadedMoviesNum();
loadLoadedSongsNum();