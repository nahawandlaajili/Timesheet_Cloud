import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import authService from './authService';

function MyLeaves({ userId }) {
  const [leaves, setLeaves] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    // Check authentication
    if (!authService.isAuthenticated()) {
      navigate('/login');
      return;
    }

    fetchMyLeaves();
  }, [userId, navigate]);

  const fetchMyLeaves = async () => {
    try {
      setLoading(true);
      const data = await authService.getMyLeaves();
      setLeaves(data);
      setError(null);
    } catch (err) {
      console.error('Error fetching leaves:', err);
      if (err.message.includes('Authentication failed')) {
        navigate('/login');
      } else {
        setError('Failed to fetch leave requests. Please try again later.');
      }
    } finally {
      setLoading(false);
    }
  };

  if (loading) return <div className="loading">Loading leave requests...</div>;
  if (error) return <div className="error">{error}</div>;

  return (
    <div className="my-leaves">
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '20px' }}>
        <h2>My Leave Requests</h2>
        <button 
          onClick={fetchMyLeaves}
          style={{ 
            padding: '8px 16px', 
            backgroundColor: '#007bff', 
            color: 'white', 
            border: 'none', 
            borderRadius: '4px', 
            cursor: 'pointer' 
          }}
        >
          Refresh
        </button>
      </div>
      
      {leaves.length === 0 ? (
        <p>No leave requests found.</p>
      ) : (
        <table style={{ width: '100%', borderCollapse: 'collapse', border: '1px solid #ddd' }}>
          <thead>
            <tr style={{ backgroundColor: '#f5f5f5' }}>
              <th style={{ padding: '12px', border: '1px solid #ddd' }}>ID</th>
              <th style={{ padding: '12px', border: '1px solid #ddd' }}>Start Date</th>
              <th style={{ padding: '12px', border: '1px solid #ddd' }}>End Date</th>
              <th style={{ padding: '12px', border: '1px solid #ddd' }}>Days</th>
              <th style={{ padding: '12px', border: '1px solid #ddd' }}>Status</th>
              <th style={{ padding: '12px', border: '1px solid #ddd' }}>Created</th>
            </tr>
          </thead>
          <tbody>
            {leaves.map(l => (
              <tr key={l.id}>
                <td style={{ padding: '12px', border: '1px solid #ddd' }}>{l.id}</td>
                <td style={{ padding: '12px', border: '1px solid #ddd' }}>{new Date(l.startDate).toLocaleDateString()}</td>
                <td style={{ padding: '12px', border: '1px solid #ddd' }}>{new Date(l.endDate).toLocaleDateString()}</td>
                <td style={{ padding: '12px', border: '1px solid #ddd' }}>{l.daysRequested}</td>
                <td style={{ padding: '12px', border: '1px solid #ddd' }}>
                  <span style={{ 
                    padding: '4px 8px', 
                    borderRadius: '4px',
                    backgroundColor: l.status === 'APPROVED' ? '#d4edda' : l.status === 'REJECTED' ? '#f8d7da' : '#fff3cd',
                    color: l.status === 'APPROVED' ? '#155724' : l.status === 'REJECTED' ? '#721c24' : '#856404'
                  }}>
                    {l.status}
                  </span>
                </td>
                <td style={{ padding: '12px', border: '1px solid #ddd' }}>{new Date(l.createdAt).toLocaleDateString()}</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
}

export default MyLeaves;