var query;
var type;
var offset = 0;
var loading = false;
var finished = false;
var contentsContainer;

var startLoading = function () {
    loading = true;
    document.getElementById("loading-spinner").style.display = "inline-block";
}

var stopLoading = function () {
    loading = false;
    document.getElementById("loading-spinner").style.display = "none";
}

var loadContents = function () {
    startLoading();
    $.ajax({
        url: "/search",
        method: "POST",
        data: {
            query: query,
            type: type,
            offset: offset
        },
        success: function (response) {
            stopLoading();

            var tempDom = document.createElement('div');
            tempDom.innerHTML = response;
            var loadedNum = tempDom.getElementsByClassName("search-card").length;

            if(loadedNum < 15) {
                finished = true;

                if(offset == 0 && loadedNum == 0)
                    document.getElementById("no-search-results").style.display = "inline-block";
            }

            if(loadedNum > 0) {
                contentsContainer.innerHTML += response;
                offset += loadedNum;
            }
        },
        fail: function (jqXHR, textStatus) {
            stopLoading();
            alert("Request failed: " + textStatus);
        }
    });
}

window.addEventListener("load", function () {
    contentsContainer = document.getElementById("search-res-container");
    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);

    query = urlParams.get('query');
    type = urlParams.get('type');

    if(!finished && !loading)
        loadContents();

    window.addEventListener("scroll", function () {
        if(window.scrollY > (document.body.offsetHeight - window.outerHeight))
            if(!finished && !loading)
                loadContents();
    });
});
