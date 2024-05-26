import React, {useState} from "react";
import {useSelector} from "react-redux";
import {Navigate} from "react-router-dom";
import {useCSVReader} from "react-papaparse";
import http from "../../http-common.js";


const FileUpload = () => {
    const [serverUploadSummary, setServerUploadSummary] = useState(null);
    const { CSVReader } = useCSVReader();
    const user = useSelector(state => state.auth.user);

    // If user is not logged in, redirect to the login page
    if (!user) {
        return <Navigate to="/login" />;
    }

    const handleDataUpload = async (data) => {
        const validData = data.slice(1).map(item => {
            item[0] = item[0].replace(/([A-Z])/g, ' $1').trim();
            return item;
        });

        const payload = { data: validData };
        try {
            const response = await http.post("/data/import", payload)
                .then(response => {
                    let performanceData = response.data;
                    setServerUploadSummary(performanceData);
                })
                .catch(error => {
                    console.log(error);
                });
        }
        catch (error) {
            console.error("Error uploading data", error);
        }
    };

    return (
        <div className="container-md mt-3">
            <div className="col-md-5">
                <CSVReader
                    onUploadAccepted={(results) => {
                        console.log('---------------------------');
                        console.log(results);
                        console.log('---------------------------');
                        handleDataUpload(results.data);
                    }}
                >
                    {({
                          getRootProps,
                          acceptedFile,
                          ProgressBar,
                          getRemoveFileProps,
                      }) => (
                        <>
                            <div className="input-group">
                                <input id="fileUploadForm1" type="file" className="form-control" {...getRootProps()} />
                                <button className="btn btn-outline-danger" type="button" {...getRemoveFileProps()}>Remove</button>
                            </div>
                            <label>{acceptedFile ? acceptedFile.name : ""}</label>
                            <ProgressBar />
                        </>
                    )}
                </CSVReader>
            </div>
            <div>
                {serverUploadSummary && (
                    <div>
                        <p>Data Entries Received: {serverUploadSummary.dataEntriesReceived}</p>
                        <p>Data Entries Ignored: {serverUploadSummary.dataEntriesIgnored}</p>
                        <p>Ignored Types: {Array.from(serverUploadSummary.ignoredTypes).join(', ')}</p>
                        <p>Total Time Elapsed: {serverUploadSummary.totalTimeElapsed / 1000} s</p>
                    </div>
                )}
            </div>
        </div>
    );
};


export default FileUpload;