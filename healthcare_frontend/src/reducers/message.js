import { SET_MESSAGE, CLEAR_MESSAGE } from "../actions/type.js";

const initialState = {};

export default function message(state = initialState, action) {
    const { type, payload } = action;

    switch (type) {
        case SET_MESSAGE:
            return { message: payload };
        case CLEAR_MESSAGE:
            return { message: "" };
        default:
            return state;
    }
}