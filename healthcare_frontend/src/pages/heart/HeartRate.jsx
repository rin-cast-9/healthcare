import React, { useState, useEffect } from 'react';
import MainComponent from '../chart/MainComponent.jsx';
import http from "../../http-common.js";

const HeartRate = () => {
    const [data, setData] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {

        const response = http.get("heart/getheartrate")
            .then(response => {
                setData(response.data);
                setLoading(false);
            })
            .catch(error => {
                setError(error);
                setLoading(false);
                console.log(error);
            });

        console.log(response.data);
    }, []);

    if (loading) {
        return <div>Loading...</div>;
    }

    if (error) {
        return <div>Error fetching data: {error.message}</div>;
    }

    console.log(data);

    return <MainComponent initialData={data} />;
};

export default HeartRate;
