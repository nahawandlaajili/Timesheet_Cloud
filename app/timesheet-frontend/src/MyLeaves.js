import React, { useEffect, useState } from "react";
import axios from "axios";

function MyLeaves({ userId }) {
    const [leaves, setLeaves] = useState([]);

    useEffect(() => {
        axios.get(`/api/leaves/user/${userId}`)
            .then((res) => setLeaves(res.data))
            .catch((err) => console.error(err));
    }, [userId]);

    return (
        <div>
            <h2>My Leave Requests</h2>
            <ul>
                {leaves.map((leave) => (
                    <li key={leave.id}>
                        {leave.startDate} → {leave.endDate} ({leave.daysRequested} days) - <b>{leave.status}</b>
                    </li>
                ))}
            </ul>
        </div>
    );
}

export default MyLeaves;
