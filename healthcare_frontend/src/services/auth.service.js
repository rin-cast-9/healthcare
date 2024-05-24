import http from "../http-common.js";

function login(username, password) {
    let data = {
        username: username,
        password: password
    };
    console.log(data);
    return http
        .post("/auth/login", data)
        .then(response => {
            console.log(response.data);
            if (response.data.token) {
                localStorage.setItem("user", JSON.stringify(response.data));
            }
            return response.data;
        })
        .catch((error) => {
            console.log(error);
        });
}


function logout() {
    localStorage.removeItem("user");
}


function register(username, password) {
    let data = {
        username: username,
        password: password
    };
    return http.post("/auth/register", data);
}

const exportedObject = {
    login: login,
    logout: logout,
    register: register
};

export default exportedObject;