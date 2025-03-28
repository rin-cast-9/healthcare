import {
    REGISTER_SUCCESS,
    REGISTER_FAIL,
    LOGIN_SUCCESS,
    LOGIN_FAIL,
    LOGOUT,
    SET_MESSAGE,
} from "./type.js";
import AuthService from "../services/auth.service.js";


const register = (username, password) => (dispatch) => {
    return AuthService.register(username, password)
        .then((response) => {
            dispatch({
                type: REGISTER_SUCCESS,
            });

            dispatch({
                type: SET_MESSAGE,
                payload: response.data.message,
            });

            return Promise.resolve();
        },
        (error) => {
        const message = (error.response && error.response.data && error.response.data.message) || error.message || error.toString();

        dispatch({
            type: REGISTER_FAIL,
        });

        dispatch({
            type: SET_MESSAGE,
            payload: message,
        });

        return Promise.reject();
        }
    );
};

const login = (username, password) => (dispatch) => {
    return AuthService.login(username, password).then((data) => {
        console.log(data);
        dispatch({
            type: LOGIN_SUCCESS,
            payload: { user: data },
        });

        return Promise.resolve();
    }, (error) => {
        const message = (error.response && error.response.data && error.response.data.message) || error.message || error.toString();

        dispatch({
            type: LOGIN_FAIL,
        });

        dispatch({
            type: SET_MESSAGE,
            payload: message,
        });

        return Promise.reject();
    });
};

const logout = () => (dispatch) => {
    AuthService.logout();

    dispatch({
        type: LOGOUT,
    });
};

const exportedObject = {
    register: register,
    login: login,
    logout: logout
};


export default exportedObject;