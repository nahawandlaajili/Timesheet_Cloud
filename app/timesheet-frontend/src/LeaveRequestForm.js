import React, { useState } from "react";
import axios from "axios";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";

function LeaveRequestForm({ userId }) {
    const [startDate, setStartDate] = useState(null);
    const [endDate, setEndDate] = useState(null);
    const [message, setMessage] = useState("");

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const res = await axios.post("/api/leaves/request", null, {
                params: {
                    userId,
                    startDate: startDate.toISOString().split("T")[0],
                    endDate: endDate.toISOString().split("T")[0],
                },
            });
            setMessage("Leave request submitted: " + res.data.status);
        } catch (err) {
            setMessage("Error: " + err.response?.data?.message || err.message);
        }
    };

    return (
        <div>
            <h2>Request Leave</h2>
            <form onSubmit={handleSubmit}>
                <label>Start Date:</label>
                <DatePicker selected={startDate} onChange={(date) => setStartDate(date)} />
                <br />
                <label>End Date:</label>
                <DatePicker selected={endDate} onChange={(date) => setEndDate(date)} />
                <br />
                <button type="submit">Submit Request</button>
            </form>
            {message && <p>{message}</p>}
        </div>
    );
}

export default LeaveRequestForm;
