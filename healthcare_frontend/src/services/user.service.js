import http from "../http-common.js";

function getUserBoard() {
    return http.get("/userBoard");
}

const exportedObject = {
    getUserBoard: getUserBoard
};

export default exportedObject;