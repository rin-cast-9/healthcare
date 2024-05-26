import React, { useState, useEffect } from 'react';
import ChartComponent from './ChartComponent';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';

const MainComponent = ({ initialData }) => {
    const [startDate, setStartDate] = useState(null);
    const [endDate, setEndDate] = useState(null);
    const [filteredData, setFilteredData] = useState(initialData);

    const handleDateChange = () => {
        if (startDate && endDate) {
            const filtered = initialData.filter(item => {
                const timestamp = new Date(item.timestamp);
                console.log(item);
                return timestamp >= startDate && timestamp <= endDate;
            });
            setFilteredData(filtered);
        } else {
            setFilteredData(initialData);
        }
    };

    useEffect(() => {
        handleDateChange();
    }, [startDate, endDate, initialData]);

    return (
        <div>
            <div className="date-picker-container">
                <DatePicker
                    selected={startDate}
                    onChange={date => setStartDate(date)}
                    selectsStart
                    startDate={startDate}
                    endDate={endDate}
                    placeholderText="Select start date"
                />
                <DatePicker
                    selected={endDate}
                    onChange={date => setEndDate(date)}
                    selectsEnd
                    startDate={startDate}
                    endDate={endDate}
                    placeholderText="Select end date"
                />
            </div>
            <ChartComponent data={filteredData} />
        </div>
    );
};

export default MainComponent;