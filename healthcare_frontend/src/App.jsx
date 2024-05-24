import React from "react";
import Header from "./layout/Header.jsx";
import {BrowserRouter, Route, Routes} from "react-router-dom";
import {connect} from "react-redux";
import "bootstrap/dist/css/bootstrap.css";

import Login from "./pages/authorization/Login.jsx";
import Register from "./pages/authorization/Register.jsx";
import Profile from "./pages/authorization/Profile.jsx";

class App extends React.Component {
    render() {
        return (
            <div>
                <BrowserRouter>
                    <Header/>
                    <Routes>
                        <Route path="/login" element={<Login/>}/>
                        <Route path="/register" element={<Register/>}/>
                        <Route path="/profile" element={<Profile/>}/>
                    </Routes>
                </BrowserRouter>
            </div>
        );
    }
}

function mapStateToProps(state) {
    const { user } = state.auth;
    return { user };
}

export default connect(mapStateToProps)(App);
