import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import authService from './authService';

function LeaveRequestForm({ userId }) {
  const [formData, setFormData] = useState({
    startDate: '',
    endDate: '',
    reason: ''
  });
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [currentUser, setCurrentUser] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    // Check authentication
    if (!authService.isAuthenticated()) {
      navigate('/login');
      return;
    }

    const user = authService.getCurrentUser();
    setCurrentUser(user);
  }, [navigate]);

  const handleChange = e => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async e => {
    e.preventDefault();
    if (!currentUser) {
      setError('User not found. Please login again.');
      return;
    }
    try {
      setLoading(true);
      setError(null);

      await authService.requestLeave(
          currentUser.userId,
          formData.startDate,
          formData.endDate
      );

      alert('Leave request submitted successfully!');
      setFormData({ startDate: '', endDate: '', reason: '' });

      // Optionally redirect to My Leaves page
      navigate('/my-leaves');

    } catch (error) {
      console.error('Leave request error:', error);
      if (error.message.includes('Authentication failed')) {
        navigate('/login');
      } else {
        setError(`Error submitting leave request: ${error.message}`);
      }
    } finally {
      setLoading(false);
    }
  };

  return (
      <div className="leave-form" style={{ maxWidth: '600px', margin: '0 auto', padding: '20px' }}>
        <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '20px' }}>
          <h2>Request Leave</h2>
          {currentUser && (
              <span style={{ color: '#666' }}>User: {currentUser.name}</span>
          )}
        </div>

        {error && (
            <div style={{
              backgroundColor: '#f8d7da',
              color: '#721c24',
              padding: '12px',
              borderRadius: '4px',
              marginBottom: '20px',
              border: '1px solid #f5c6cb'
            }}>
              {error}
            </div>
        )}

        <form onSubmit={handleSubmit} style={{ display: 'flex', flexDirection: 'column', gap: '15px' }}>
          <div>
            <label style={{ display: 'block', marginBottom: '5px', fontWeight: 'bold' }}>
              Start Date:
            </label>
            <input
                type="date"
                name="startDate"
                value={formData.startDate}
                onChange={handleChange}
                required
                disabled={loading}
                style={{
                  width: '100%',
                  padding: '8px',
                  border: '1px solid #ddd',
                  borderRadius: '4px',
                  fontSize: '14px'
                }}
            />
          </div>
          <div>
            <label style={{ display: 'block', marginBottom: '5px', fontWeight: 'bold' }}>
              End Date:
            </label>
            <input
                type="date"
                name="endDate"
                value={formData.endDate}
                onChange={handleChange}
                required
                disabled={loading}
                min={formData.startDate}
                style={{
                  width: '100%',
                  padding: '8px',
                  border: '1px solid #ddd',
                  borderRadius: '4px',
                  fontSize: '14px'
                }}
            />
          </div>
            <div>
                <label style={{ display: 'block', marginBottom: '5px', fontWeight: 'bold' }}>
                    Reason (Optional):
                </label>
                <textarea
                    name="reason"
                    value={formData.reason}
                    onChange={handleChange}
                    disabled={loading}
                    rows="4"
                    placeholder="Enter reason for leave request..."
                    style={{
                        width: '100%',
                        padding: '8px',
                        border: '1px solid #ddd',
                        borderRadius: '4px',
                        fontSize: '14px',
                        resize: 'vertical'
                    }}
                />
            </div>
            <button
                type="submit"
                disabled={loading}
                style={{
                    padding: '12px 24px',
                    backgroundColor: loading ? '#ccc' : '#28a745',
                    color: 'white',
                    border: 'none',
                    borderRadius: '4px',
                    fontSize: '16px',
                    cursor: loading ? 'not-allowed' : 'pointer',
                    marginTop: '10px'
                }}
            >
                {loading ? 'Submitting...' : 'Submit Leave Request'}
            </button>

      </form>
    </div>
  );
}

export default LeaveRequestForm;