import React, { useEffect, useState } from 'react';

function MyLeaves({ userId }) {
  const [leaves, setLeaves] = useState([]);

  useEffect(() => {
    // Simulate fetching leaves
    const mockData = [
      { id: 1, startDate: '2025-02-01', endDate: '2025-02-03', status: 'Approved' },
      { id: 2, startDate: '2025-04-12', endDate: '2025-04-14', status: 'Pending' },
    ];
    setLeaves(mockData);
  }, [userId]);

  return (
    <div className="my-leaves">
      <h2>My Leave Requests</h2>
      {leaves.length === 0 ? (
        <p>No leave requests found.</p>
      ) : (
        <table>
          <thead>
            <tr>
              <th>ID</th>
              <th>Start Date</th>
              <th>End Date</th>
              <th>Status</th>
            </tr>
          </thead>
          <tbody>
            {leaves.map(l => (
              <tr key={l.id}>
                <td>{l.id}</td>
                <td>{l.startDate}</td>
                <td>{l.endDate}</td>
                <td>{l.status}</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
}

export default MyLeaves;
