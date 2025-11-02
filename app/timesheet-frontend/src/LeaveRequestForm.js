import React, { useState } from 'react';
import axios from 'axios';

function LeaveRequestForm({ userId }) {
  const [formData, setFormData] = useState({
    startDate: '',
    endDate: '',
    reason: ''
  });

  const handleChange = e => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async e => { // <-- async here
    e.preventDefault();
    try {
      const response = await axios.post(`http://localhost:8082/api/leaves/request`, {
        userId,
        startDate: formData.startDate,
        endDate: formData.endDate
      },
          {
            withCredentials: true // <-- this is the key
          });
      console.log('Leave request submitted:', response.data);
      alert('Leave request submitted!');
      setFormData({ startDate: '', endDate: '', reason: '' });
    } catch (error) {
      console.error('Leave request error:', error.response?.data || error.message);
      alert('Error submitting leave request.');
    }
  };

  return (
    <div className="leave-form">
      <h2>Request Leave</h2>
      <form onSubmit={handleSubmit}>
        <label>Start Date:</label>
        <input type="date" name="startDate" value={formData.startDate} onChange={handleChange} required />

        <label>End Date:</label>
        <input type="date" name="endDate" value={formData.endDate} onChange={handleChange} required />

        <label>Reason:</label>
        <textarea name="reason" value={formData.reason} onChange={handleChange} required />

        <button type="submit">Submit</button>
      </form>
    </div>
  );
}

export default LeaveRequestForm;