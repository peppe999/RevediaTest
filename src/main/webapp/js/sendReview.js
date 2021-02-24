var rating = 0;
var stars = document.getElementById("new-review-stars").getElementsByClassName("star");
var sendBtn = document.getElementById("send-review-btn");
var contentName;
var contentAlbum;
var contentType;
var sendReviewUrl;

var loadReviewParameters = function () {
    sendReviewUrl = window.location.pathname;
    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);

    if(sendReviewUrl.search("song") != -1) {
        contentName = urlParams.get("name");
        contentAlbum = urlParams.get("album");
        contentType = "song";
    }
    else if(sendReviewUrl.search("album") != -1) {
        contentAlbum = urlParams.get("id");
        contentType = "album";
    }
    else if(sendReviewUrl.search("movie") != -1) {
        contentName = urlParams.get("title");
        contentType = "movie";
    }
    else if(sendReviewUrl.search("book") != -1) {
        contentName = urlParams.get("title");
        contentType = "book";
    }
}

var changeStars = function (index, isHover) {
    if(!isHover)
        rating = index + 1;

    for(let i = 0; i <= index; i++)
        stars[i].classList.add("selected-star");

    for(let i = index + 1; i < stars.length; i++)
        stars[i].classList.remove("selected-star");

}

var loadStarsEvent = function () {
    for(let i = 0; i < stars.length; i++) {
        stars[i].addEventListener("click", changeStars.bind(this, i, false));
        stars[i].addEventListener("mouseover", changeStars.bind(this, i, true));
    }
}

loadStarsEvent();
loadReviewParameters();

document.getElementById("new-review-stars").addEventListener("mouseleave", function () {
    changeStars(rating - 1, true);
});

var sendAlbumReview = function (text) {
    $.ajax({
        url: requestUrl + "/sendreview",
        method: "POST",
        data: {
            id: contentAlbum,
            text: text,
            rating: rating
        },
        success: function (response) {
            document.getElementById("my-review-container").innerHTML = response;
        },
        fail: function (jqXHR, textStatus) {
            alert("Request failed: " + textStatus);
        }
    });
}

var sendSongReview = function (text) {
    $.ajax({
        url: requestUrl + "/sendreview",
        method: "POST",
        data: {
            name: contentName,
            album: contentAlbum,
            text: text,
            rating: rating
        },
        success: function (response) {
            document.getElementById("my-review-container").innerHTML = response;
        },
        fail: function (jqXHR, textStatus) {
            alert("Request failed: " + textStatus);
        }
    });
}

var sendMovieReview = function (text) {
    $.ajax({
        url: requestUrl + "/sendreview",
        method: "POST",
        data: {
            title: contentName,
            text: text,
            rating: rating
        },
        success: function (response) {
            document.getElementById("my-review-container").innerHTML = response;
        },
        fail: function (jqXHR, textStatus) {
            alert("Request failed: " + textStatus);
        }
    });
}

var sendBookReview = function (text) {
    $.ajax({
        url: requestUrl + "/sendreview",
        method: "POST",
        data: {
            title: contentName,
            text: text,
            rating: rating
        },
        success: function (response) {
            document.getElementById("my-review-container").innerHTML = response;
        },
        fail: function (jqXHR, textStatus) {
            alert("Request failed: " + textStatus);
        }
    });
}

sendBtn.addEventListener("click", function () {
    var text = document.getElementById("new-review-text");
    var errorLbl = document.getElementById("send-review-error-lbl");

    if(rating == 0 && text.value == "") {
        errorLbl.innerText = "Devi inserire il testo della recensione e la tua valutazione";
        errorLbl.style.display = "block";
        return;
    }
    else if(rating == 0 && text.value != "") {
        errorLbl.innerText = "Devi inserire la tua valutazione";
        errorLbl.style.display = "block";
        return;
    }
    else if(rating != 0 && text.value == "") {
        errorLbl.innerText = "Devi inserire il testo della recensione";
        errorLbl.style.display = "block";
        return;
    }

    document.getElementById("loading-review-spinner").style.display = "inline-block";

    switch (contentType) {
        case "album":
            sendAlbumReview(text.value);
            break;
        case "song":
            sendSongReview(text.value);
            break;
        case "movie":
            sendMovieReview(text.value);
            break;
        case "book":
            sendBookReview(text.value);
            break;
    }
});