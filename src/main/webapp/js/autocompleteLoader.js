var searchBoxQuery = "";
var searchBoxLoading = false;
var searchBoxLoadingFinished = false;
var searchBoxContentsContainer;
var autocompleteSearchBox;

var loadSearchBoxContents = function () {
    $.ajax({
        url: "/search/autocomplete",
        method: "POST",
        myQuery: searchBoxQuery,
        data: {
            query: searchBoxQuery
        },
        success: function (response) {
            if(this.myQuery == searchBoxQuery) {
                searchBoxContentsContainer.style.display = "block";
                searchBoxContentsContainer.innerHTML = response;
            }
        },
        fail: function (jqXHR, textStatus) {
            alert("Request failed: " + textStatus);
        }
    });
}

searchBoxContentsContainer = document.getElementsByClassName("search-autocomplete-box")[0];
autocompleteSearchBox = document.getElementById("search-field-box");

autocompleteSearchBox.addEventListener("input", function () {
    searchBoxQuery = this.value;

    if(searchBoxQuery == "")
        searchBoxContentsContainer.style.display = "none";
    else
        loadSearchBoxContents();

});

autocompleteSearchBox.addEventListener("focusin", function () {
    if(searchBoxQuery != "")
        searchBoxContentsContainer.style.display = "block";
});

document.body.addEventListener("click", function (event){
    if(searchBoxContentsContainer != event.target && !searchBoxContentsContainer.contains(event.target) && autocompleteSearchBox != event.target)
        searchBoxContentsContainer.style.display = "none";
});