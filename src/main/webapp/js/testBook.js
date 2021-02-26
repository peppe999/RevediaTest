
function Book(title, numberOfPages, description, publishingHouse, artist, genres, link){
	this.title = title;
	this.numberOfPages = numberOfPages;
	this.description = description;
	this.publishingHouse = publishingHouse;
	this.artist = artist;
	this.genres = genres;
	this.link = link;
}



var getBooks = function(){
	var genres = ["thriller", "horror", "fantasy", "history", "novel"];
	for(var i = 0; i < genres.length; i++){
		getBooksByGenre(genres[i]);
	}
}



var getBooksByGenre = function(genere){
	
	$.ajax({
		url:"https://www.googleapis.com/books/v1/volumes?q=subject:"+genere+"&maxResults=10",
		method: "GET",
		contentType: "application/JSON",
		async: false,
		success: function (response) {
            console.log(response.items.length);

            	var items = response.items;
               	for(var i=0; i< items.length; i++) {
               		var genre = genere.charAt(0).toUpperCase() + genere.substr(1);
               		if(genre == "History")
               			genre = "Storia";
               		var generi = [];
               		generi.push(genre);

                    uploadBook(items[i], generi);

               }
            },
	});

}

var uploadImage = function(id, url){
	$.ajax({
		url:"loader/upbook/img",
		method:"POST",
		async: false,
		data: {
			id: id,
			url: url
		},
		success: function(response){
			console.log("load image");
		},
	});

}


var uploadBook = function(item, generi){
	console.log(generi)
	var book = new Book(item.volumeInfo.title, item.volumeInfo.pageCount, item.volumeInfo.description, item.volumeInfo.publisher, item.volumeInfo.authors[0], generi, item.volumeInfo.previewLink);
	$.ajax({
		url:"loader/upbook",
		method: "POST",
		contentType: "application/JSON",
		async: false,
		data: JSON.stringify(book),
		success: function (id) {
            console.log("load success");
            if(id == -1)
            	return;

			uploadImage(id, item.volumeInfo.imageLinks.thumbnail.replace(/zoom=1/g, "zoom=0"));
		}
	});
}

window.addEventListener('load', function () {
	getBooks();
})