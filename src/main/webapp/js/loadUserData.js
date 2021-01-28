var loadUserAvgQuality = function () {
    $.ajax({
        url: "user/avgquality",
        method: "POST",
        contentType: "application/json",
        success: function(avgQuality){
            var content;
            if(avgQuality == null)
                content = "---";
            else
                content = Math.round(avgQuality * 100) / 100;

            document.getElementById("quality").innerText = content + "%";
        },
        fail: function( jqXHR, textStatus ) {
            alert( "Request failed: " + textStatus );
        }
    });
}

var loadUserAvgRating = function () {
    $.ajax({
        url: "user/avgrating",
        method: "POST",
        contentType: "application/json",
        success: function(avgRating){
            var content;
            if(avgRating == null)
                content = "---";
            else
                content = Math.round(avgRating * 100) / 100;

            document.getElementById("like-perc").innerText = content + "%";
        },
        fail: function( jqXHR, textStatus ) {
            alert( "Request failed: " + textStatus );
        }
    });
}

var loadUserNumRatedReviews = function () {
    $.ajax({
        url: "user/numratedreviews",
        method: "POST",
        contentType: "application/json",
        success: function(numRatedReviews){
            var content;
            if(numRatedReviews == null)
                content = "---";
            else
                content = numRatedReviews;

            document.getElementById("num-rated-reviews").innerText = content;
        },
        fail: function( jqXHR, textStatus ) {
            alert( "Request failed: " + textStatus );
        }
    });
}

var loadUserBestReview = function () {
    $.ajax({
        url: "user/bestreview",
        method: "POST",
        contentType: "application/json",
        success: function(bestReview){
            var content;
            if(bestReview == null)
                content = "---";
            else
                content = bestReview;

            document.getElementById("best-review").innerText = content;
        },
        fail: function( jqXHR, textStatus ) {
            alert( "Request failed: " + textStatus );
        }
    });
}

var loadUserFavCat = function () {
    $.ajax({
        url: "user/favcat",
        method: "POST",
        contentType: "application/json",
        success: function(favCat){
            var styleClass;
            if(favCat == null)
                return;

            if(favCat == 'Musica')
                styleClass = "fa-music";
            else if(favCat == 'Film')
                styleClass = "fa-film";
            else
                styleClass = "fa-book";

            document.getElementById("fav-cat").innerText = favCat;
            document.getElementById("fav-cat-icn").classList.add(styleClass);
        },
        fail: function( jqXHR, textStatus ) {
            alert( "Request failed: " + textStatus );
        }
    });
}

var loadUserFavGenreByCat = function () {
    $.ajax({
        url: "user/favgenrecat",
        method: "POST",
        contentType: "application/json",
        success: function(favGenreCat){
            if(favGenreCat == null)
                return;

            var musicContent;
            var movieContent;
            var bookContent;

            if(favGenreCat.Musica == null)
                musicContent = '---';
            else
                musicContent = favGenreCat.Musica;

            if(favGenreCat.Film == null)
                movieContent = '---';
            else
                movieContent = favGenreCat.Film;

            if(favGenreCat.Libri == null)
                bookContent = '---';
            else
                bookContent = favGenreCat.Libri;

            document.getElementById("fav-music-genre").innerText = musicContent;
            document.getElementById("fav-movie-genre").innerText = movieContent;
            document.getElementById("fav-book-genre").innerText = bookContent;
        },
        fail: function( jqXHR, textStatus ) {
            alert( "Request failed: " + textStatus );
        }
    });
}

var loadUserNumReviews = function () {
    $.ajax({
        url: "user/numreviews",
        method: "POST",
        contentType: "application/json",
        success: function(response){
            if(response == null)
                return;

            var contentNumReviews;
            var contentNumReviewsPerc;

            if(response.numReviews == null)
                contentNumReviews = "---";
            else
                contentNumReviews = response.numReviews;

            if(response.numReviewsPerc == null)
                contentNumReviewsPerc = "---";
            else
                contentNumReviewsPerc = Math.round(response.numReviewsPerc * 100) / 100;

            document.getElementById("num-reviews-perc").innerText = contentNumReviewsPerc + "%";
            document.getElementById("num-reviews").innerText = contentNumReviews;
        },
        fail: function( jqXHR, textStatus ) {
            alert( "Request failed: " + textStatus );
        }
    });
}

window.addEventListener('load', function () {
    loadUserAvgQuality();
    loadUserNumReviews();
    loadUserNumRatedReviews();
    loadUserBestReview();
    loadUserAvgRating();
    loadUserFavCat();
    loadUserFavGenreByCat();
});