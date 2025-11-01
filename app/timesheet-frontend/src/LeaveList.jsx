import React, { useEffect, useState } from "react";
import { getUserLeaves } from "../api/leaveService";

export default function LeaveList({ userId }) {
  const [leaves, setLeaves] = useState([]);

  useEffect(() => {
    getUserLeaves(userId).then((res) => setLeaves(res.data));
  }, [userId]);

  return (
    <div className="max-w-2xl mx-auto mt-8 bg-white shadow-lg rounded-2xl p-6">
      <h2 className="text-xl font-bold text-gray-700 mb-4">
        My Leave Requests
      </h2>
      {leaves.length === 0 ? (
        <p className="text-gray-500">No leave requests yet.</p>
      ) : (
        <table className="w-full border">
          <thead className="bg-gray-200">
            <tr>
              <th className="p-2 border">Start</th>
              <th className="p-2 border">End</th>
              <th className="p-2 border">Days</th>
              <th className="p-2 border">Status</th>
            </tr>
          </thead>
          <tbody>
            {leaves.map((leave) => (
              <tr key={leave.id} className="text-center">
                <td className="border p-2">{leave.startDate}</td>
                <td className="border p-2">{leave.endDate}</td>
                <td className="border p-2">{leave.daysRequested}</td>
                <td
                  className={`border p-2 ${
                    leave.status === "APPROVED"
                      ? "text-green-600"
                      : leave.status === "REJECTED"
                      ? "text-red-600"
                      : "text-yellow-600"
                  }`}
                >
                  {leave.status}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
}
