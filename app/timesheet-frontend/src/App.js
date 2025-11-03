// src/App.js
import React, { useState, useEffect } from 'react';
import { Routes, Route, Link, useNavigate } from 'react-router-dom';
import './App.css';

import Dashboard from './Dashboard';
import Login from './login';
import Signup from './signup';
import Profile from './Profile';
import Timesheets from './timesheets_component';
import LeaveRequestForm from './LeaveRequestForm';
import MyLeaves from './MyLeaves';
import AdminLeaveRequests from './AdminLeaveRequests';

import authService from './authService';

// ------------------------
// Navigation Component
// ------------------------
function Navigation({ isAuthenticated, currentUser, onLogout }) {
  return (
    <header className="App-header">
      <h1>Timesheet Cloud</h1>
      <nav>
        <ul>
          {isAuthenticated ? (
            <>
              <li><Link to="/">Dashboard</Link></li>
              <li><Link to="/timesheets">Timesheets</Link></li>
              <li><Link to="/profile">Profile</Link></li>
              <li><Link to="/request-leave">Leave Request</Link></li>
              <li><Link to="/my-leaves">My Leaves</Link></li>
              <li><Link to="/admin/leaves">Admin Panel</Link></li>
              <li className="welcome-user">
                <span>Welcome, {currentUser?.name || 'User'}!</span>
                <button onClick={onLogout}>Logout</button>
              </li>
            </>
          ) : (
            <>
              <li><Link to="/login">Login</Link></li>
              <li><Link to="/signup">Sign Up</Link></li>
            </>
          )}
        </ul>
      </nav>
    </header>
  );
}

// ------------------------
// Main App Component
// ------------------------
function App() {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [currentUser, setCurrentUser] = useState(null);
  const navigate = useNavigate();

  // For routes that need user ID
  const userId = currentUser?.id || 1;

  // Check auth status on load and periodically
  useEffect(() => {
    const checkAuth = () => {
      const authenticated = authService.isAuthenticated();
      setIsAuthenticated(authenticated);
      setCurrentUser(authenticated ? authService.getCurrentUser() : null);
    };

    checkAuth();
    const interval = setInterval(checkAuth, 30000); // every 30 sec
    return () => clearInterval(interval);
  }, []);

  // Logout handler
  const handleLogout = () => {
    authService.logout();
    setIsAuthenticated(false);
    setCurrentUser(null);
    navigate('/login');
  };

  return (
    <div className="App">
      <Navigation 
        isAuthenticated={isAuthenticated} 
        currentUser={currentUser} 
        onLogout={handleLogout} 
      />

      <main>
        <Routes>
          <Route path="/" element={<Dashboard />} />
          <Route path="/timesheets" element={<Timesheets />} />
          <Route path="/login" element={<Login />} />
          <Route path="/signup" element={<Signup />} />
          <Route path="/profile" element={<Profile user={currentUser} />} />
          <Route path="/request-leave" element={<LeaveRequestForm userId={userId} />} />
          <Route path="/my-leaves" element={<MyLeaves userId={userId} />} />
          <Route path="/admin/leaves" element={<AdminLeaveRequests />} />
        </Routes>
      </main>

      <footer>
        <p>&copy; 2025 Timesheet Cloud. All rights reserved.</p>
      </footer>
    </div>
  );
}

export default App;
