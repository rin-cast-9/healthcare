import React, { useRef, useEffect, useState } from "react";
import { Chart as ChartJS, TimeScale, LinearScale, PointElement, LineElement, Title, Tooltip, Legend } from 'chart.js';
import { Scatter } from 'react-chartjs-2';
import 'chartjs-adapter-moment';

// Register the necessary components with Chart.js
ChartJS.register(TimeScale, LinearScale, PointElement, LineElement, Title, Tooltip, Legend);

const ChartComponent = ({ data }) => {
    const chartRef = useRef(null);
    const [chartData, setChartData] = useState({
        datasets: []
    });

    useEffect(() => {
        if (data) {
            // Transform the data into the format needed for the scatter plot
            const transformedData = {
                datasets: [{
                    label: 'Health Data',
                    data: data.map(item => ({
                        x: item.timestamp,
                        y: item.value
                    })),
                    backgroundColor: 'rgba(253,90,90,0.6)',
                }]
            };
            setChartData(transformedData);
        }
    }, [data]);

    // Function to handle destruction of chart
    useEffect(() => {
        return () => {
            if (chartRef.current) {
                chartRef.current.destroy();
            }
        };
    }, []);

    const options = {
        responsive: true,
        scales: {
            x: {
                type: 'time',
                time: {
                    unit: 'day',
                },
                title: {
                    display: true,
                    text: 'Date'
                }
            },
            y: {
                title: {
                    display: true,
                    text: 'Value'
                }
            }
        },
        plugins: {
            title: {
                display: true,
                text: 'Heart rate chart'
            },
            tooltip: {
                callbacks: {
                    label: function(tooltipItem) {
                        return `Value: ${tooltipItem.raw.y}`;
                    }
                }
            }
        }
    };

    return <Scatter ref={chartRef} data={chartData} options={options} />;
};

export default ChartComponent;