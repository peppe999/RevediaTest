var btn = document.getElementById("confirmBtn");
btn.addEventListener('click', function () {
    var firstName = document.getElementById("firstNameInp");
    var lastName = document.getElementById("lastNameInp");
    var mail = document.getElementById("mailInp");
    var psw = document.getElementById("pswInp");

    $.ajax({
        url: "user/editData",
        method: "POST",
        data: {
            firstName: firstName.value,
            lastName: lastName.value,
            mail: mail.value,
            psw: psw.value
        },
        success: function(response){
            var elements = document.getElementById("pswGroup").getElementsByClassName("form-input-msg");
            for(var i = 0; i < elements.length; i++)
                elements.item(i).remove();

            if(response != null && response != "ok") {
                psw.classList.add("form-input-error");
                document.getElementById("pswGroup").innerHTML += '<span class="form-input-msg form-input-error-msg">' + response + '</span>';
            }
            else if(response != null && response == "ok") {
                psw.classList.remove("form-input-error");
                document.getElementById("pswGroup").innerHTML += '<span class="form-input-msg">Cambiamento effettuato</span>';
            }
        },
        fail: function( jqXHR, textStatus ) {
            alert( "Request failed: " + textStatus );
        }
    });
})