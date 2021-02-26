var modality = 0;
var order = 0;
var tipology = 0;
var numberOfPages;
var actualPage = 1;
var loading = false;
var manageQuery = "";
var contentsContainer;
var numI;
var alphaModeBtn;
var timeModeBtn;
var ascOrderBtn;
var descOrderBtn;
var albumTypeBtn;
var songTypeBtn;
var movieTypeBtn;
var bookTypeBtn;
var searchMyContentsBox;

var startLoading = function () {
    loading = true;
    document.getElementById("load-spinner").style.display = "inline-block";
}

var stopLoading = function () {
    loading = false;
    document.getElementById("load-spinner").style.display = "none";
}

var validatePage = function () {
    if(actualPage > numberOfPages)
        actualPage = numberOfPages;

    if(actualPage < 1)
        actualPage = 1;

    updatePageNumberBox();
}

var createHTMLCard = function (name) {
    var iconName;

    switch (tipology) {
        case 0:
            iconName = "fa-dot-circle-o";
            break;
        case 1:
            iconName = "fa-music";
            break;
        case 2:
            iconName = "fa-film";
            break;
        case 3:
            iconName = "fa-book";
            break;
    }

    html = '<div class="row">' +
                '<div class="col">' +
                    '<div class="d-inline-flex align-items-center user-content-row">' +
                        '<h3 class="card-title"><i class="fa ' + iconName + ' card-icon"></i>' + name + '</h3>' +
                        '<div class="ml-auto"><button class="btn card-edit-btn" type="button">Modifica</button><button class="btn card-delete-btn" type="button">Elimina</button></div>' +
                    '</div>' +
                '</div>' +
            '</div>';

    return html;
}

var loadAlbumContents = function () {
    startLoading();
    $.ajax({
        url: "/manage/album/explore",
        method: "POST",
        data: {
            query: manageQuery,
            page: actualPage,
            modality: modality,
            order: order
        },
        success: function (response) {
            stopLoading();

            if(response == null)
                return;

            var html = "";
            for(let i = 0; i < response.length; i++) {
                html += createHTMLCard(response[i].name);
            }
            contentsContainer.innerHTML = html;

            //connect edit and delete btns
        },
        fail: function (jqXHR, textStatus) {
            stopLoading();
            alert("Request failed: " + textStatus);
        }
    });
}

var loadSongContents = function () {
    startLoading();
    $.ajax({
        url: "/manage/song/explore",
        method: "POST",
        data: {
            query: manageQuery,
            page: actualPage,
            modality: modality,
            order: order
        },
        success: function (response) {
            stopLoading();

            if(response == null)
                return;

            var html = "";
            for(let i = 0; i < response.length; i++) {
                html += createHTMLCard(response[i].name);
            }
            contentsContainer.innerHTML = html;

            //connect edit and delete btns
        },
        fail: function (jqXHR, textStatus) {
            stopLoading();
            alert("Request failed: " + textStatus);
        }
    });
}

var loadMovieContents = function () {
    startLoading();
    $.ajax({
        url: "/manage/movie/explore",
        method: "POST",
        data: {
            query: manageQuery,
            page: actualPage,
            modality: modality,
            order: order
        },
        success: function (response) {
            stopLoading();

            if(response == null)
                return;

            var html = "";
            for(let i = 0; i < response.length; i++) {
                html += createHTMLCard(response[i].title);
            }
            contentsContainer.innerHTML = html;

            //connect edit and delete btns
        },
        fail: function (jqXHR, textStatus) {
            stopLoading();
            alert("Request failed: " + textStatus);
        }
    });
}

var loadBookContents = function () {
    startLoading();
    $.ajax({
        url: "/manage/book/explore",
        method: "POST",
        data: {
            query: manageQuery,
            page: actualPage,
            modality: modality,
            order: order
        },
        success: function (response) {
            stopLoading();

            if(response == null)
                return;

            var html = "";
            for(let i = 0; i < response.length; i++) {
                html += createHTMLCard(response[i].title);
            }
            contentsContainer.innerHTML = html;

            //connect edit and delete btns
        },
        fail: function (jqXHR, textStatus) {
            stopLoading();
            alert("Request failed: " + textStatus);
        }
    });
}

var loadContents = function () {
    switch (tipology) {
        case 0:
            loadAlbumContents();
            break;
        case 1:
            loadSongContents();
            break;
        case 2:
            loadMovieContents();
            break;
        case 3:
            loadBookContents();
            break;
    }
}

var loadTipologyContents = function () {
    startLoading();
    $.ajax({
        url: "/manage/explore/numberOfContents",
        method: "POST",
        data: {
            query: manageQuery,
            type: tipology
        },
        success: function (response) {
            numberOfPages = response;
            validatePage();
            document.getElementById("page-number-h").innerText = numberOfPages;
            loadContents();
        },
        fail: function (jqXHR, textStatus) {
            alert("Request failed: " + textStatus);
        }
    });
}

var resizeInput = function(){
    document.getElementById("hidden-page-number").innerHTML = numI.value;
    numI.style.width = document.getElementById("hidden-page-number").clientWidth + 10 + "px";
};

var updatePageNumberBox = function () {
    numI.value = actualPage;
    resizeInput();
}

var inputChangePage = function () {
    if(loading)
        return;

    var previousPage = actualPage;

    actualPage = numI.value;
    validatePage();

    if(previousPage == actualPage)
        return;

    loadContents();
}

var resetModalityBtn = function () {
    alphaModeBtn.classList.remove("active-filter");
    timeModeBtn.classList.remove("active-filter");
}

var resetOrderBtn = function () {
    ascOrderBtn.classList.remove("active-filter");
    descOrderBtn.classList.remove("active-filter");
}

var resetTipologyBtn = function () {
    albumTypeBtn.classList.remove("active-filter");
    songTypeBtn.classList.remove("active-filter");
    movieTypeBtn.classList.remove("active-filter");
    bookTypeBtn.classList.remove("active-filter");
}

window.addEventListener("load", function () {
    contentsContainer = document.getElementById("dynamic-explore-container");
    numI = document.getElementById("page-number-input");
    searchMyContentsBox = document.getElementById("search-my-content-box");

    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);

    if(urlParams.has('page'))
        actualPage = parseInt(urlParams.get('page'));

    genre = urlParams.get('genre');

    loadTipologyContents();

    document.getElementById("page-right-arrow").addEventListener("click", function () {
        if(loading)
            return;

        if(actualPage < numberOfPages) {
            actualPage++;
            updatePageNumberBox();
            loadContents();
        }
    });

    document.getElementById("page-left-arrow").addEventListener("click", function () {
        if(loading)
            return;

        if(actualPage > 1) {
            actualPage--;
            updatePageNumberBox();
            loadContents();
        }
    });

    numI.addEventListener("input", function(){
        resizeInput();
    });

    numI.addEventListener("keydown", function (event) {
       if(event.key === "Enter")
           inputChangePage();
    });

    numI.addEventListener("focusout", function (event) {
        inputChangePage();
    });

    searchMyContentsBox.addEventListener("keydown", function (event) {
        if(event.key === "Enter") {
            manageQuery = searchMyContentsBox.value;
            actualPage = 1;
            loadTipologyContents();
        }
    });

    searchMyContentsBox.addEventListener("focusout", function (event) {
        manageQuery = searchMyContentsBox.value;
        actualPage = 1;
        loadTipologyContents();
    });

    alphaModeBtn = document.getElementById("alpha-order-btn");
    alphaModeBtn.addEventListener("click", function () {
        if(loading)
            return;

        resetModalityBtn();
        modality = 0;
        this.classList.add("active-filter");
        loadContents();
    });

    timeModeBtn = document.getElementById("time-order-btn");
    timeModeBtn.addEventListener("click", function () {
        if(loading)
            return;

        resetModalityBtn();
        modality = 1;
        this.classList.add("active-filter");
        loadContents();
    });

    ascOrderBtn = document.getElementById("asc-order-btn");
    ascOrderBtn.addEventListener("click", function () {
        if(loading)
            return;

        resetOrderBtn();
        order = 0;
        this.classList.add("active-filter");
        loadContents();
    });

    descOrderBtn = document.getElementById("desc-order-btn");
    descOrderBtn.addEventListener("click", function () {
        if(loading)
            return;

        resetOrderBtn();
        order = 1;
        this.classList.add("active-filter");
        loadContents();
    });


    albumTypeBtn = document.getElementById("album-type-filter-btn");
    albumTypeBtn.addEventListener("click", function () {
        if(loading)
            return;

        resetTipologyBtn();
        tipology = 0;
        actualPage = 1;
        this.classList.add("active-filter");
        loadTipologyContents();
    });

    songTypeBtn = document.getElementById("song-type-filter-btn");
    songTypeBtn.addEventListener("click", function () {
        if(loading)
            return;

        resetTipologyBtn();
        tipology = 1;
        actualPage = 1;
        this.classList.add("active-filter");
        loadTipologyContents();
    });

    movieTypeBtn = document.getElementById("movie-type-filter-btn");
    movieTypeBtn.addEventListener("click", function () {
        if(loading)
            return;

        resetTipologyBtn();
        tipology = 2;
        actualPage = 1;
        this.classList.add("active-filter");
        loadTipologyContents();
    });

    bookTypeBtn = document.getElementById("book-type-filter-btn");
    bookTypeBtn.addEventListener("click", function () {
        if(loading)
            return;

        resetTipologyBtn();
        tipology = 3;
        actualPage = 1;
        this.classList.add("active-filter");
        loadTipologyContents();
    });

});