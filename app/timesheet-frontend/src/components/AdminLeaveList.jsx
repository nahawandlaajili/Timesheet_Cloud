import React, { useEffect, useState } from "react";
import { getAllLeaves, approveLeave, rejectLeave } from "../api/leaveService";

export default function AdminLeaveList() {
  const [leaves, setLeaves] = useState([]);

  const fetchLeaves = () => {
    getAllLeaves().then((res) => setLeaves(res.data));
  };

  useEffect(() => {
    fetchLeaves();
  }, []);

  const handleApprove = async (id) => {
    await approveLeave(id);
    fetchLeaves();
  };

  const handleReject = async (id) => {
    await rejectLeave(id);
    fetchLeaves();
  };

  return (
    <div className="max-w-3xl mx-auto mt-8 bg-white shadow-lg rounded-2xl p-6">
      <h2 className="text-xl font-bold text-gray-700 mb-4">
        Admin - Manage Leave Requests
      </h2>
      {leaves.length === 0 ? (
        <p className="text-gray-500">No leave requests found.</p>
      ) : (
        <table className="w-full border">
          <thead className="bg-gray-200">
            <tr>
              <th className="p-2 border">User ID</th>
              <th className="p-2 border">Start</th>
              <th className="p-2 border">End</th>
              <th className="p-2 border">Days</th>
              <th className="p-2 border">Status</th>
              <th className="p-2 border">Actions</th>
            </tr>
          </thead>
          <tbody>
            {leaves.map((leave) => (
              <tr key={leave.id} className="text-center">
                <td className="border p-2">{leave.userId}</td>
                <td className="border p-2">{leave.startDate}</td>
                <td className="border p-2">{leave.endDate}</td>
                <td className="border p-2">{leave.daysRequested}</td>
                <td className="border p-2">{leave.status}</td>
                <td className="border p-2 space-x-2">
                  {leave.status === "PENDING" && (
                    <>
                      <button
                        onClick={() => handleApprove(leave.id)}
                        className="bg-green-600 text-white px-3 py-1 rounded hover:bg-green-700"
                      >
                        Approve
                      </button>
                      <button
                        onClick={() => handleReject(leave.id)}
                        className="bg-red-600 text-white px-3 py-1 rounded hover:bg-red-700"
                      >
                        Reject
                      </button>
                    </>
                  )}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
}