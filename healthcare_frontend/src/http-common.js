import axios from "axios";

let token = "";
let user = JSON.parse(localStorage.getItem("user"));

if (user && user.token) {
    token = user.token;
}

export default axios.create({
    baseURL: "http://localhost:8080/api/health",
    headers: {
        "Content-Type": "application/json",
        "Authorization": token ? `Bearer ${token}` : ""
    }
});