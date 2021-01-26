const button = document.getElementById("signinButton");
button.onclick = function ()
{
    let password = document.getElementById("password1").value;
    let confirmPassword = document.getElementById("password2").value;

    if(password !== confirmPassword)
    {
        console.log(password)
        alert("Le password non corrispondono");
        return false;
    }
}