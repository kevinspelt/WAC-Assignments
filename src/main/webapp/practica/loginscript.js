function login() {
    var formData = new FormData(document.querySelector("#loginform"));
    var encData = new URLSearchParams(formData.entries());

    fetch("http://localhost:1337/restservices/authentication", { method: 'POST', body: encData })
        .then(function(response) {
            if (response.ok) return response.json();
            else throw "Wrong username/password";
        })
        .then(myJson => window.sessionStorage.setItem("sessionToken", myJson.JWT))
        .catch(error => console.log(error));
}

document.querySelector("#login").addEventListener("click", login);