
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './timesheet_management_styles.css';

const Timesheets = () => {
  const [timesheets, setTimesheets] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [newTimesheet, setNewTimesheet] = useState({
    userId: '',
    date: '',
    hoursWorked: '',
    description: ''
  });

  useEffect(() => {
    fetchTimesheets();
  }, []);

  const fetchTimesheets = async () => {
    try {
      setLoading(true);
      const response = await axios.get('/timesheets');
      setTimesheets(response.data);
      setError(null);
    } catch (err) {
      console.error('Error fetching timesheets:', err);
      setError('Failed to fetch timesheets. Please try again later.');
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
      const response = await axios.post('/timesheets', newTimesheet);
      setTimesheets([...timesheets, response.data]);
      // Reset form
      setNewTimesheet({
        userId: '',
        date: '',
        hoursWorked: '',
        description: ''
      });
    } catch (err) {
      console.error('Error creating timesheet:', err);
      setError('Failed to create timesheet. Please try again.');
    }
  };

  if (loading) return <div className="loading">Loading timesheets...</div>;
  if (error) return <div className="error">{error}</div>;

  return (
    <div className="timesheets-container">
      <h2>Timesheets</h2>
      
      <div className="timesheet-form-container">
        <h3>Add New Timesheet</h3>
        <form onSubmit={handleSubmit} className="timesheet-form">
          <div className="form-group">
            <label htmlFor="userId">User ID</label>
            <input
              type="text"
              id="userId"
              name="userId"
              value={newTimesheet.userId}
              onChange={handleInputChange}
              required
            />
          </div>
          
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
