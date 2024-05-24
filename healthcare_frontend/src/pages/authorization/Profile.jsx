import React from "react";
import { Navigate } from "react-router-dom";
import { connect } from "react-redux"


const Profile = ({ user }) => {
    if (!user) {
        return <Navigate to="/login" />;
    }

    return (
        <div className="container">
            <header>
                <h3>
                    Profile <strong>{user.username}</strong>, good morning!
                </h3>
            </header>
        </div>
    );
};

const mapStateToProps = state => ({
    user: state.auth.user
});

export default connect(mapStateToProps)(Profile);