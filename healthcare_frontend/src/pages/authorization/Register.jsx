import React, { useState } from "react";
import { Navigate } from "react-router-dom";
import { connect } from "react-redux";
import auth from "../../actions/auth.js";


const Register = ({ dispatch, isRegistered, message }) => {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [role, setRole] = useState("");
    const [successful, setSuccessful] = useState(undefined);

    const handleRegister = (e) => {
        e.preventDefault();
        setSuccessful(false);

        dispatch(auth.register(username, password, role))
            .then(() => {
                setSuccessful(true);
                window.location.reload();
            })
            .catch(() => {
                setSuccessful(false);
            });
    };

    if (isRegistered) {
        return <Navigate to="/login" />;
    }

    return (
        <div className="container-md mt-3">
            <div className="col-md-5">
                <form onSubmit={handleRegister}>
                    <div className="form-group mt-2">
                        <input
                            type="text"
                            className="form-control"
                            name="username"
                            placeholder="Login"
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                            required
                        />
                    </div>
                    <div className="form-group mt-2">
                        <input
                            type="password"
                            className="form-control"
                            name="password"
                            placeholder="Password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            required
                        />
                    </div>
                    <div className="form-group mt-2">
                        <button className="btn btn-primary btn-block">Sign up</button>
                    </div>
                    {message && successful !== undefined && (
                        <div className="form-group">
                            <div className={successful ? "alert alert-success" : "alert alert-danger"} role="alert">
                                {message}
                            </div>
                        </div>
                    )}
                </form>
            </div>
        </div>
    );
};

const mapStateToProps = state => ({
    isRegistered: state.auth.isRegistered,
    message: state.message.message
});

export default connect(mapStateToProps)(Register);