import React, { useEffect, useState } from "react";
import { useNavigate } from 'react-router-dom';
import authService from './authService';

function AdminLeaveRequests() {
    const [leaves, setLeaves] = useState([]);

    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [actionLoading, setActionLoading] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        // Check authentication
        if (!authService.isAuthenticated()) {
            navigate('/login');
            return;
        }

        fetchLeaves();
    }, [navigate]);

    const fetchLeaves = async () => {
        try {
            setLoading(true);
            const data = await authService.getAllLeaves();
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

    const handleAction = async (leaveId, action) => {
        try {
            setActionLoading(leaveId);

            if (action === 'approve') {
                await authService.approveLeave(leaveId);
            } else if (action === 'reject') {
                await authService.rejectLeave(leaveId);
            }

            // Refresh the list
            await fetchLeaves();
        } catch (err) {
            console.error(`Error ${action}ing leave:`, err);
            if (err.message.includes('Authentication failed')) {
                navigate('/login');
            } else {
                alert(`Error ${action}ing leave request: ${err.message}`);
            }
        } finally {
            setActionLoading(null);
        }
    };
    if (loading) return <div className="loading">Loading leave requests...</div>;
    if (error) return <div className="error">{error}</div>;

    return (
        <div style={{ padding: '20px' }}>
            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '20px' }}>
                <h2>Admin Leave Requests</h2>
                <button
                    onClick={fetchLeaves}
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
                        <th style={{ padding: '12px', border: '1px solid #ddd' }}>User ID</th>
                        <th style={{ padding: '12px', border: '1px solid #ddd' }}>Start Date</th>
                        <th style={{ padding: '12px', border: '1px solid #ddd' }}>End Date</th>
                        <th style={{ padding: '12px', border: '1px solid #ddd' }}>Days</th>
                        <th style={{ padding: '12px', border: '1px solid #ddd' }}>Status</th>
                        <th style={{ padding: '12px', border: '1px solid #ddd' }}>Created</th>
                        <th style={{ padding: '12px', border: '1px solid #ddd' }}>Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    {leaves.map((leave) => (
                        <tr key={leave.id}>
                            <td style={{ padding: '12px', border: '1px solid #ddd' }}>{leave.userId}</td>
                            <td style={{ padding: '12px', border: '1px solid #ddd' }}>{new Date(leave.startDate).toLocaleDateString()}</td>
                            <td style={{ padding: '12px', border: '1px solid #ddd' }}>{new Date(leave.endDate).toLocaleDateString()}</td>
                            <td style={{ padding: '12px', border: '1px solid #ddd' }}>{leave.daysRequested}</td>
                            <td style={{ padding: '12px', border: '1px solid #ddd' }}>
                                    <span style={{
                                        padding: '4px 8px',
                                        borderRadius: '4px',
                                        backgroundColor: leave.status === 'APPROVED' ? '#d4edda' : leave.status === 'REJECTED' ? '#f8d7da' : '#fff3cd',
                                        color: leave.status === 'APPROVED' ? '#155724' : leave.status === 'REJECTED' ? '#721c24' : '#856404'
                                    }}>
                                        {leave.status}
                                    </span>
                            </td>
                            <td style={{ padding: '12px', border: '1px solid #ddd' }}>{new Date(leave.createdAt).toLocaleDateString()}</td>
                            <td style={{ padding: '12px', border: '1px solid #ddd' }}>
                                {leave.status === "PENDING" && (
                                    <div style={{ display: 'flex', gap: '8px' }}>
                                        <button
                                            onClick={() => handleAction(leave.id, "approve")}
                                            disabled={actionLoading === leave.id}
                                            style={{
                                                padding: '6px 12px',
                                                backgroundColor: actionLoading === leave.id ? '#ccc' : '#28a745',
                                                color: 'white',
                                                border: 'none',
                                                borderRadius: '4px',
                                                cursor: actionLoading === leave.id ? 'not-allowed' : 'pointer',
                                                fontSize: '12px'
                                            }}
                                        >
                                            {actionLoading === leave.id ? 'Processing...' : 'Approve'}
                                        </button>
                                        <button
                                            onClick={() => handleAction(leave.id, "reject")}
                                            disabled={actionLoading === leave.id}
                                            style={{
                                                padding: '6px 12px',
                                                backgroundColor: actionLoading === leave.id ? '#ccc' : '#dc3545',
                                                color: 'white',
                                                border: 'none',
                                                borderRadius: '4px',
                                                cursor: actionLoading === leave.id ? 'not-allowed' : 'pointer',
                                                fontSize: '12px'
                                            }}
                                        >
                                            {actionLoading === leave.id ? 'Processing...' : 'Reject'}
                                        </button>
                                    </div>
                                )}
                                {leave.status !== "PENDING" && (
                                    <span style={{ color: '#666', fontSize: '12px' }}>No actions available</span>
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

export default AdminLeaveRequests;