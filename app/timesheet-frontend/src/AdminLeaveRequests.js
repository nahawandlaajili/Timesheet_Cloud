import React, { useEffect, useState } from "react";
import axios from "axios";

function AdminLeaveRequests() {
    const [leaves, setLeaves] = useState([]);

    const fetchLeaves = () => {
        axios.get("/api/leaves")
            .then((res) => setLeaves(res.data))
            .catch((err) => console.error(err));
    };

    useEffect(() => {
        fetchLeaves();
    }, []);

    const handleAction = async (leaveId, action) => {
        try {
            const url = `/api/leaves/${leaveId}/${action}`;
            await axios.put(url);
            fetchLeaves();
        } catch (err) {
            console.error(err);
        }
    };

    return (
        <div>
            <h2>Admin Leave Requests</h2>
            <table border="1">
                <thead>
                <tr>
                    <th>User</th>
                    <th>Start</th>
                    <th>End</th>
                    <th>Days</th>
                    <th>Status</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                {leaves.map((leave) => (
                    <tr key={leave.id}>
                        <td>{leave.userId}</td>
                        <td>{leave.startDate}</td>
                        <td>{leave.endDate}</td>
                        <td>{leave.daysRequested}</td>
                        <td>{leave.status}</td>
                        <td>
                            {leave.status === "PENDING" && (
                                <>
                                    <button onClick={() => handleAction(leave.id, "approve")}>Approve</button>
                                    <button onClick={() => handleAction(leave.id, "reject")}>Reject</button>
                                </>
                            )}
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
}

export default AdminLeaveRequests;
