function onSignIn(googleUser) {
    var f = document.createElement("form");
    f.setAttribute('method',"post");
    f.setAttribute('action',"/googleLogin");

    var tokenInput = document.createElement("input");
    tokenInput.setAttribute('type',"text");
    tokenInput.setAttribute('name',"idtoken");
    tokenInput.setAttribute('value', googleUser.getAuthResponse().id_token);

    googleUser.disconnect()

    f.appendChild(tokenInput);

    document.body.appendChild(f);

    f.submit();
}