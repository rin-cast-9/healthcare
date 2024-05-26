import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import authActions from "../actions/auth";
import { connect, useDispatch } from "react-redux";


function Header({ user }) {
    const dispatch = useDispatch();
    const [currentUser, setCurrentUser] = useState(null);

    useEffect(() => {
        if (user) {
            setCurrentUser(user);
        }
    }, [user]);

    const logout = () => {
        dispatch(authActions.logout());
        window.location.reload();
    };

    return (
        <nav className="navbar navbar-project navbar-expand-lg navbar-light" style={{ background: "#d9b9fa"}}>
            {currentUser ? (
                <>
                    <div className="ms-3">
                        <Link className="navbar-brand" to="/getHeartRateData">Heart rate</Link>
                    </div>
                    <div className="ms-3">
                        <Link className="navbar-brand" to="/import">Import data</Link>
                    </div>
                </>
            ) : (<div></div>)}

            {currentUser ? (
                <div className="my-2 my-lg-0">
                    <Link className="navbar-brand btn" to="/profile">{currentUser.username}</Link>
                    <button className="navbar-brand btn" onClick={logout}>Log out</button>
                </div>
            ) : (
                <div className="my-2 my-lg-0">
                    <Link to="/register" className="nav-link navbar-brand btn navbar-brand-button">Sign up</Link>
                    <Link to="/login" className="nav-link navbar-brand btn navbar-brand-button">Log in</Link>
                </div>
            )}
        </nav>
    );
}

function mapStateToProps(state) {
    const { user } = state.auth;
    return { user };
}

export default connect(mapStateToProps)(Header);