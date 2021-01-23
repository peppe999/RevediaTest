/*var btn = document.getElementById("sortingCollapser");
btn.addEventListener("click", function(){
    this.style.display = "none";
});


var elements = document.getElementsByClassName("collapseElement");

for(var i = 0; i < elements.length; i++) {
    elements[i].addEventListener("click", function(){
        btn.style.display = "block";
    });
}

document.getElementById("test").addEventListener("click", function(){
    this.style.order = 4;
});*/
/*[].forEach.call(elements, function(){
    .addEventListener("click", function() {
        alert("nculu a tia");
        btn.style.display = "block";
    });
});*/

/*elements.forEach(element => {
    alert(element);
    element.addEventListener("click", function() {
        alert("nculu a tia");
        btn.style.display = "block";
    });
});*/

var numI = document.getElementById("page-number-input");

numI.addEventListener("input", function(){
    //this.style.width = (this.value.length * 12 + 16) + "px";
    document.getElementById("hidden-page-number").innerHTML = this.value;
    this.style.width = document.getElementById("hidden-page-number").clientWidth + 10 + "px";
    //alert(this.offsetWidth);
});