import './timesheet_management_styles.css';
import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import authService from './authService';

const Timesheets = () => {
  const [timesheets, setTimesheets] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [newTimesheet, setNewTimesheet] = useState({
    date: '',
    hoursWorked: '',
    description: ''
  });
  const [currentUser, setCurrentUser] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    // Check authentication
    if (!authService.isAuthenticated()) {
      navigate('/login');
      return;
    }

    // Get current user info
    const user = authService.getCurrentUser();
    setCurrentUser(user);
    
    // Set userId in newTimesheet
    if (user) {
      setNewTimesheet(prev => ({
        ...prev,
        userId: user.userId
      }));
    }

    // Test connection first
    testConnection();
  }, [navigate]);

  const testConnection = async () => {
    try {
      await authService.testConnection();
      fetchTimesheets();
    } catch (error) {
      console.error('Connection test failed:', error);
      setError(`Connection failed: ${error.message}`);
      setLoading(false);
    }
  };

  const fetchTimesheets = async () => {
    try {
      setLoading(true);
      const data = await authService.getTimesheets();
      setTimesheets(data);
      setError(null);
    } catch (err) {
      console.error('Error fetching timesheets:', err);
      if (err.message.includes('Authentication failed')) {
        navigate('/login');
      } else {
        setError('Failed to fetch timesheets. Please try again later.');
      }
    } finally {
      setLoading(false);
    }
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setNewTimesheet({
      ...newTimesheet,
      [name]: value
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await authService.createTimesheet(newTimesheet);
      setTimesheets([...timesheets, response]);
      // Reset form (keep userId)
      setNewTimesheet({
        userId: currentUser?.userId || '',
        date: '',
        hoursWorked: '',
        description: ''
      });
      setError(null);
    } catch (err) {
      console.error('Error creating timesheet:', err);
      if (err.message.includes('Authentication failed')) {
        navigate('/login');
      } else {
        setError('Failed to create timesheet. Please try again.');
      }
    }
  };

  const handleLogout = () => {
    authService.logout();
    navigate('/login');
  };

  if (loading) return <div className="loading">Loading timesheets...</div>;
  if (error) return <div className="error">{error}</div>;

  return (
    <div className="timesheets-container">
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '20px' }}>
        <h2>Timesheets</h2>
        <div style={{ display: 'flex', alignItems: 'center', gap: '15px' }}>
          {currentUser && (
            <span>Welcome, {currentUser.name}!</span>
          )}
          <button onClick={handleLogout} style={{ padding: '8px 16px', backgroundColor: '#dc3545', color: 'white', border: 'none', borderRadius: '4px', cursor: 'pointer' }}>
            Logout
          </button>
        </div>
      </div>
      
      <div className="timesheet-form-container">
        <h3>Add New Timesheet</h3>
        <form onSubmit={handleSubmit} className="timesheet-form">
          
          <div className="form-group">
            <label htmlFor="date">Date</label>
            <input
              type="date"
              id="date"
              name="date"
              value={newTimesheet.date}
              onChange={handleInputChange}
              required
            />
          </div>
          
          <div className="form-group">
            <label htmlFor="hoursWorked">Hours Worked</label>
            <input
              type="number"
              id="hoursWorked"
              name="hoursWorked"
              min="0"
              max="24"
              step="0.5"
              value={newTimesheet.hoursWorked}
              onChange={handleInputChange}
              required
            />
          </div>
          
          <div className="form-group">
            <label htmlFor="description">Description</label>
            <textarea
              id="description"
              name="description"
              value={newTimesheet.description}
              onChange={handleInputChange}
              required
            />
          </div>
          
          <button type="submit" className="submit-btn">Add Timesheet</button>
        </form>
      </div>

      <div className="timesheets-list">
        <h3>Your Timesheets</h3>
        {timesheets.length === 0 ? (
          <p className="no-data">No timesheets found.</p>
        ) : (
          <table className="timesheets-table">
            <thead>
              <tr>
                <th>User ID</th>
                <th>Date</th>
                <th>Hours Worked</th>
                <th>Description</th>
              </tr>
            </thead>
            <tbody>
              {timesheets.map((timesheet, index) => (
                <tr key={timesheet.id || index}>
                  <td>{timesheet.userId}</td>
                  <td>{new Date(timesheet.date).toLocaleDateString()}</td>
                  <td>{timesheet.hoursWorked}</td>
                  <td>{timesheet.description}</td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </div>
    </div>
  );
};

export default Timesheets;
