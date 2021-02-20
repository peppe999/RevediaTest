var modality = 0;
var order = 0;
var numberOfPages;
var actualPage = 1;
var loading = false;
var genre;
var contentsContainer;
var numI;
var alphaModeBtn;
var timeModeBtn;
var ascOrderBtn;
var descOrderBtn;

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

var loadContents = function () {
    startLoading();
    $.ajax({
        url: "/books/explore",
        method: "POST",
        data: {
            genre: genre,
            page: actualPage,
            modality: modality,
            order: order
        },
        success: function (response) {
            stopLoading();
            contentsContainer.innerHTML = response;
        },
        fail: function (jqXHR, textStatus) {
            stopLoading();
            alert("Request failed: " + textStatus);
        }
    });
}

var loadTipologyContents = function () {
    startLoading();
    $.ajax({
        url: "/books/explore/numberOfContents",
        method: "GET",
        data: {
            genre: genre
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

window.addEventListener("load", function () {
    contentsContainer = document.getElementById("dynamic-explore-container");
    numI = document.getElementById("page-number-input");
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

});