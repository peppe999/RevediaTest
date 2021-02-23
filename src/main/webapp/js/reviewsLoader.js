var requestUrl;
var offset = 0;
var loading = false;
var finished = false;
var contentsContainer;
var name;
var album;
var type;

var startLoading = function () {
    loading = true;
    document.getElementById("loading-spinner").style.display = "inline-block";
}

var stopLoading = function () {
    loading = false;
    document.getElementById("loading-spinner").style.display = "none";
}

var loadAlbumReviews = function () {
    startLoading();
    $.ajax({
        url: requestUrl,
        method: "POST",
        data: {
            id: album,
            offset: offset
        },
        success: function (response) {
            stopLoading();
            manageResult(response);
        },
        fail: function (jqXHR, textStatus) {
            stopLoading();
            alert("Request failed: " + textStatus);
        }
    });
}

var loadSongReviews = function () {
    startLoading();
    $.ajax({
        url: requestUrl,
        method: "POST",
        data: {
            name: name,
            album: album,
            offset: offset
        },
        success: function (response) {
            stopLoading();
            manageResult(response);
        },
        fail: function (jqXHR, textStatus) {
            stopLoading();
            alert("Request failed: " + textStatus);
        }
    });
}

var loadMovieReviews = function () {
    startLoading();
    $.ajax({
        url: requestUrl,
        method: "POST",
        data: {
            title: name,
            offset: offset
        },
        success: function (response) {
            stopLoading();
            manageResult(response);
        },
        fail: function (jqXHR, textStatus) {
            stopLoading();
            alert("Request failed: " + textStatus);
        }
    });
}

var loadBookReviews = function () {
    startLoading();
    $.ajax({
        url: requestUrl,
        method: "POST",
        data: {
            title: name,
            offset: offset
        },
        success: function (response) {
            stopLoading();
            manageResult(response);
        },
        fail: function (jqXHR, textStatus) {
            stopLoading();
            alert("Request failed: " + textStatus);
        }
    });
}

var rateAlbumReview = function (user, likeBtn, dislikeBtn, rate, likeCounter, dislikeCounter) {
    if(likeBtn.classList.contains("selected-like-icon") && rate)
        return;

    if(dislikeBtn.classList.contains("selected-like-icon") && !rate)
        return;

    $.ajax({
        url: requestUrl + "/rateReview",
        method: "POST",
        data: {
            id: album,
            user: user,
            rating: rate
        },
        success: function (response) {
            if(!response)
                return;

            if(rate) {
                if(dislikeBtn.classList.contains("selected-like-icon"))
                    dislikeCounter.innerText = parseInt(dislikeCounter.innerText) - 1;

                dislikeBtn.classList.remove("selected-like-icon");
                likeBtn.classList.add("selected-like-icon");
                likeCounter.innerText = parseInt(likeCounter.innerText) + 1;
            }
            else {
                if(likeBtn.classList.contains("selected-like-icon"))
                    likeCounter.innerText = parseInt(likeCounter.innerText) - 1;

                dislikeBtn.classList.add("selected-like-icon");
                likeBtn.classList.remove("selected-like-icon");
                dislikeCounter.innerText = parseInt(dislikeCounter.innerText) + 1;
            }

        },
        fail: function (jqXHR, textStatus) {
            alert("Request failed: " + textStatus);
        }
    });
}

var rateSongReview = function (user, likeBtn, dislikeBtn, rate, likeCounter, dislikeCounter) {
    if(likeBtn.classList.contains("selected-like-icon") && rate)
        return;

    if(dislikeBtn.classList.contains("selected-like-icon") && !rate)
        return;

    $.ajax({
        url: requestUrl + "/rateReview",
        method: "POST",
        data: {
            name: name,
            album: album,
            user: user,
            rating: rate
        },
        success: function (response) {
            if(!response)
                return;

            if(rate) {
                if(dislikeBtn.classList.contains("selected-like-icon"))
                    dislikeCounter.innerText = parseInt(dislikeCounter.innerText) - 1;

                dislikeBtn.classList.remove("selected-like-icon");
                likeBtn.classList.add("selected-like-icon");
                likeCounter.innerText = parseInt(likeCounter.innerText) + 1;
            }
            else {
                if(likeBtn.classList.contains("selected-like-icon"))
                    likeCounter.innerText = parseInt(likeCounter.innerText) - 1;

                dislikeBtn.classList.add("selected-like-icon");
                likeBtn.classList.remove("selected-like-icon");
                dislikeCounter.innerText = parseInt(dislikeCounter.innerText) + 1;
            }

        },
        fail: function (jqXHR, textStatus) {
            alert("Request failed: " + textStatus);
        }
    });
}

var rateMovieReview = function (user, likeBtn, dislikeBtn, rate, likeCounter, dislikeCounter) {
    if(likeBtn.classList.contains("selected-like-icon") && rate)
        return;

    if(dislikeBtn.classList.contains("selected-like-icon") && !rate)
        return;

    $.ajax({
        url: requestUrl + "/rateReview",
        method: "POST",
        data: {
            title: name,
            user: user,
            rating: rate
        },
        success: function (response) {
            if(!response)
                return;

            if(rate) {
                if(dislikeBtn.classList.contains("selected-like-icon"))
                    dislikeCounter.innerText = parseInt(dislikeCounter.innerText) - 1;

                dislikeBtn.classList.remove("selected-like-icon");
                likeBtn.classList.add("selected-like-icon");
                likeCounter.innerText = parseInt(likeCounter.innerText) + 1;
            }
            else {
                if(likeBtn.classList.contains("selected-like-icon"))
                    likeCounter.innerText = parseInt(likeCounter.innerText) - 1;

                dislikeBtn.classList.add("selected-like-icon");
                likeBtn.classList.remove("selected-like-icon");
                dislikeCounter.innerText = parseInt(dislikeCounter.innerText) + 1;
            }

        },
        fail: function (jqXHR, textStatus) {
            alert("Request failed: " + textStatus);
        }
    });
}

var rateBookReview = function (user, likeBtn, dislikeBtn, rate, likeCounter, dislikeCounter) {
    if(likeBtn.classList.contains("selected-like-icon") && rate)
        return;

    if(dislikeBtn.classList.contains("selected-like-icon") && !rate)
        return;

    $.ajax({
        url: requestUrl + "/rateReview",
        method: "POST",
        data: {
            title: name,
            user: user,
            rating: rate
        },
        success: function (response) {
            if(!response)
                return;

            if(rate) {
                if(dislikeBtn.classList.contains("selected-like-icon"))
                    dislikeCounter.innerText = parseInt(dislikeCounter.innerText) - 1;

                dislikeBtn.classList.remove("selected-like-icon");
                likeBtn.classList.add("selected-like-icon");
                likeCounter.innerText = parseInt(likeCounter.innerText) + 1;
            }
            else {
                if(likeBtn.classList.contains("selected-like-icon"))
                    likeCounter.innerText = parseInt(likeCounter.innerText) - 1;

                dislikeBtn.classList.add("selected-like-icon");
                likeBtn.classList.remove("selected-like-icon");
                dislikeCounter.innerText = parseInt(dislikeCounter.innerText) + 1;
            }

        },
        fail: function (jqXHR, textStatus) {
            alert("Request failed: " + textStatus);
        }
    });
}

var rateReview = function (user, likeBtn, dislikeBtn, rate, likeCounter, dislikeCounter) {
    switch (type) {
        case "album":
            rateAlbumReview(user, likeBtn, dislikeBtn, rate, likeCounter, dislikeCounter);
            break;
        case "song":
            rateSongReview(user, likeBtn, dislikeBtn, rate, likeCounter, dislikeCounter);
            break;
        case "movie":
            rateMovieReview(user, likeBtn, dislikeBtn, rate, likeCounter, dislikeCounter);
            break;
        case "book":
            rateBookReview(user, likeBtn, dislikeBtn, rate, likeCounter, dislikeCounter);
            break;
    }
}

var connectReviews = function (startIndex) {
    var cardList = contentsContainer.getElementsByClassName("review-card");

    for(let i = startIndex; i < cardList.length; i++) {
        let card = cardList[i];
        let user = card.getElementsByClassName("nickname-span")[0].innerText;
        let likeBtn = card.getElementsByClassName("like-bts")[0];
        let dislikeBtn = card.getElementsByClassName("dislike-btn")[0];
        let likeCounter = card.getElementsByClassName("like-counter")[0];
        let dislikeCounter = card.getElementsByClassName("dislike-counter")[0];

        likeBtn.addEventListener("click", rateReview.bind(this, user, likeBtn, dislikeBtn, true, likeCounter, dislikeCounter));
        dislikeBtn.addEventListener("click", rateReview.bind(this, user, likeBtn, dislikeBtn, false, likeCounter, dislikeCounter));
    }
}

var checkLogin = function (startIndex) {
    $.ajax({
        url: "/checkLogin",
        method: "POST",
        success: function (response) {
            if(!response)
                return;

            connectReviews(startIndex);
        },
        fail: function (jqXHR, textStatus) {
            alert("Request failed: " + textStatus);
        }
    });
}

var manageResult = function (response) {
    var tempDom = document.createElement('div');
    tempDom.innerHTML = response;
    var loadedNum = tempDom.getElementsByClassName("review-card").length;

    if(loadedNum < 15) {
        finished = true;

        if(offset == 0 && loadedNum == 0)
            document.getElementById("no-reviews").style.display = "inline-block";
    }

    if(loadedNum > 0) {
        contentsContainer.innerHTML += response;
        offset += loadedNum;
    }

    checkLogin(offset - loadedNum);
}

var loadContents = function () {
    switch (type) {
        case "album":
            loadAlbumReviews();
            break;
        case "song":
            loadSongReviews();
            break;
        case "movie":
            loadMovieReviews();
            break;
        case "book":
            loadBookReviews();
            break;
    }
}

var loadParameters = function () {
    requestUrl = window.location.pathname;
    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);

    if(requestUrl.search("song") != -1) {
        name = urlParams.get("name");
        album = urlParams.get("album");
        type = "song";
    }
    else if(requestUrl.search("album") != -1) {
        album = urlParams.get("id");
        type = "album";
    }
    else if(requestUrl.search("movie") != -1) {
        name = urlParams.get("title");
        type = "movie";
    }
    else if(requestUrl.search("book") != -1) {
        name = urlParams.get("title");
        type = "book";
    }
}

window.addEventListener("load", function () {
    contentsContainer = document.getElementById("reviews-container");

    loadParameters();

    if(!finished && !loading)
        loadContents();

    window.addEventListener("scroll", function () {
        if(window.scrollY > (document.body.offsetHeight - window.outerHeight))
            if(!finished && !loading)
                loadContents();
    });
});
