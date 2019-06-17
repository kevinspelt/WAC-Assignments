function initPage() {
    document.querySelectorAll(".form").forEach(f => f.style.display = "none");
    document.getElementById("addSaveFormButton").addEventListener("click", function () {
        saveAddForm();
    });
    document.getElementById("editFillFormButton").addEventListener("click",function () {
        fillEditForm();
    });
    document.getElementById("editSaveFormButton").addEventListener("click",function () {
        saveEditForm();
    });
    document.getElementById("deleteFormButton").addEventListener("click", function () {
        deleteForm();
    });
    fetch("https://ipapi.co/json/")
        .then(response => response.json())
        .then(function (myJson) {
            const dataIP = document.querySelectorAll("td.dataIP");

            for(let i = 0; i < dataIP.length; i++) {
                const td = dataIP[i];
                td.textContent = myJson[td.id];

                if(td.id === "city") {
                    td.addEventListener("click", function () {
                        showWeather(myJson.longitude, myJson.latitude, myJson.city);
                    })
                }
            }

            showWeather(myJson.longitude, myJson.latitude, myJson.city);
            loadCountries();
        });
}

function showWeather(longitude, latitude, city) {
    const dataWeather = document.querySelectorAll("td.dataWeather");
    const weatherHeader = document.querySelector("#weatherHeader");

    weatherHeader.textContent = "Het weer in " + city;
    window.scrollTo(0,0);

    if(window.localStorage.getItem(city) === null) {
        fetch(`https://api.openweathermap.org/data/2.5/weather?lat=${latitude}&lon=${longitude}&q=${city}&units=metric&appid=3b0d963e4598d465a0b3926b905c7cf1`)
            .then(response => response.json())
            .then(function (myJson) {
                for(let i = 0; i < dataWeather.length; i++) {
                    const td = dataWeather[i];
                    const parts = td.id.split(".");
                    td.textContent = myJson[parts[0]][parts[1]];
                }
                myJson.timestamp = Date.now();
                window.localStorage.setItem(city, JSON.stringify(myJson));
            });
    } else if(Date.now() - window.localStorage.getItem(city).timestamp > 600) {
        fetch(`https://api.openweathermap.org/data/2.5/weather?lat=${latitude}&lon=${longitude}&q=${city}&units=metric&appid=3b0d963e4598d465a0b3926b905c7cf1`)
            .then(response => response.json())
            .then(function (myJson) {
                for(let i = 0; i < dataWeather.length; i++) {
                    const td = dataWeather[i];
                    const parts = td.id.split(".");
                    td.textContent = myJson[parts[0]][parts[1]];
                }
                myJson.timestamp = Date.now();
                window.localStorage.setItem(city, JSON.stringify(myJson));
            });
    } else {
        const country = JSON.parse(window.localStorage.getItem(city));
        for(let i = 0; i < dataWeather.length; i++) {
            const td = dataWeather[i];
            const parts = td.id.split(".");
            td.textContent = country[parts[0]][parts[1]];
        }
    }
}

function loadCountries() {
    fetch("/restservices/countries", {method: "GET"})
        .then(response => Promise.all([response.status, response.json()]))
        .then(function ([status, myJson]) {
            if(status === 200) {
                const countryList = document.querySelector("#countryList");
                const countryTable = countryList.querySelector("#countryTable");
                const countryTBody = countryTable.querySelector("#countries");

                while (countryTBody.firstChild) {
                    countryTBody.removeChild(countryTBody.firstChild);
                }

                for (const country of myJson) {
                    const ph = document.createElement("tr");

                    const vlag = document.createElement("td");
                    const landcode = document.createElement("td");
                    const land = document.createElement("td");
                    const hoofdstad = document.createElement("td");
                    const regio = document.createElement("td");
                    const oppervlakte = document.createElement("td");
                    const inwoners = document.createElement("td");

                    const span = vlag.appendChild(document.createElement("span"));
                    span.setAttribute("id", "flag");
                    span.setAttribute("class", "flag-icon flag-icon-" + country.code.toLowerCase());

                    landcode.appendChild(document.createTextNode(country.code));
                    land.appendChild(document.createTextNode(country.name));
                    hoofdstad.appendChild(document.createTextNode(country.capital));
                    regio.appendChild(document.createTextNode(country.region));
                    oppervlakte.appendChild(document.createTextNode(country.surface));
                    inwoners.appendChild(document.createTextNode(country.population));

                    ph.appendChild(vlag);
                    ph.appendChild(landcode);
                    ph.appendChild(land);
                    ph.appendChild(hoofdstad);
                    ph.appendChild(regio);
                    ph.appendChild(oppervlakte);
                    ph.appendChild(inwoners);

                    ph.addEventListener("click", function () {
                        showWeather(country.lng, country.lat, country.capital);
                    });

                    countryTBody.appendChild(ph);
                }
            } else {
                console.log(myJson.error())
            }
        })
        .catch(error => console.log(error.message));
}


function hideForm(formid) {
    const forms = document.querySelectorAll(".form");
    for(let x = 0; x < forms.length; x++) {
        const form = forms[x];
        if(form.id !== formid && form.style.display !== "none") {
                form.style.display = "none";
        } else if(form.id === formid && form.style.display === "none") {
            form.style.display = "block";
        } else {
            form.style.display = "none";
        }
    }
}

function saveAddForm() {
    const form = document.getElementById("addFormForm");
    const formdata = new FormData(form);
    const encData = new URLSearchParams(formdata.entries());

    const fetchoptions = {
        method: 'POST',
        body: encData,
        headers : {
            'Authorization': 'Bearer ' +  window.sessionStorage.getItem("sessionToken")
        }
    };

    fetch("/restservices/countries/", fetchoptions)
        .then(response => Promise.all([response.status, response.json()]))
        .then(function ([status, myJson]) {
            if (status === 200) {
                console.log("SUCCESFUL");
                setTimeout(function () {
                    loadCountries()
                }, 100);
            } else if(status === 403) {
                console.log("YOU NEED USER ROLE TO DO THIS");
            } else {
                console.log(myJson.error);
            }
        })
        .catch(error => console.log(error.message));
}

function fillEditForm() {
    const form = document.getElementById("editFormForm");
    const code = form["code"].value;
    fetch("/restservices/countries/" + code, {method: "GET"})
        .then(response => Promise.all([response.status, response.json()]))
        .then(function ([status, myJson]) {
            if (status === 200) {
                form["name"].value = myJson.name;
                form["capital"].value = myJson.capital;
                form["region"].value = myJson.region;
                form["surface"].value = myJson.surface;
                form["population"].value = myJson.population;
            } else {
                console.log(myJson.error);
            }
        })
        .catch(error => console.log(error.message));
}

function saveEditForm() {
    const form = document.getElementById("editFormForm");
    const code = form["code"].value;
    const formData = new FormData(form);
    const encData = new URLSearchParams(formData.entries());

    const fetchoptions = {
        method: 'PUT',
        body: encData,
        headers : {
            'Authorization': 'Bearer ' +  window.sessionStorage.getItem("sessionToken")
        }
    };

    fetch("/restservices/countries/" + code, fetchoptions)
        .then(response => Promise.all([response.status, response.json()]))
        .then(function ([status, myJson]) {
            if (status === 200) {
                console.log("SUCCESFUL");
                setTimeout(function () {
                    loadCountries()
                }, 100);
            } else if(status === 403) {
                console.log("YOU NEED USER ROLE TO DO THIS");
            } else {
                console.log(myJson.error);
            }
        })
        .catch(error => console.log(error.message));
}

function deleteForm() {
    const form = document.getElementById("deleteFormForm");
    const code = form["code"].value;

    const fetchoptions = {
        method: 'DELETE',
        headers : {
            'Authorization': 'Bearer ' +  window.sessionStorage.getItem("sessionToken")
        }
    };

    fetch("/restservices/countries/" + code, fetchoptions)
        .then(response => Promise.all([response.status, response.json()]))
        .then(function ([status, myJson]) {
            if (status === 200) {
                console.log("SUCCESFUL");
                setTimeout(function () {
                    loadCountries()
                }, 100);
            } else if(status === 403) {
                console.log("YOU NEED USER ROLE TO DO THIS");
            } else {
                console.log(myJson.error);
            }
        })
        .catch(error => console.log(error.message));
}

window.onload = initPage();