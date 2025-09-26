import React, { useState, useEffect } from 'react';
import { BrowserRouter as Router, Routes, Route, Link, useNavigate } from 'react-router-dom';
import './App.css';
import Dashboard from './Dashboard';
import Login from './login';
import Signup from './signup';
import Timesheets from './timesheets_component';
import authService from './authService';

function Navigation() {
    const [isAuthenticated, setIsAuthenticated] = useState(false);
    const [currentUser, setCurrentUser] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        const checkAuth = () => {
            const authenticated = authService.isAuthenticated();
            setIsAuthenticated(authenticated);
            if (authenticated) {
                setCurrentUser(authService.getCurrentUser());
            } else {
                setCurrentUser(null);
            }
        };

        checkAuth();
        // Check auth status periodically
        const interval = setInterval(checkAuth, 30000);
        return () => clearInterval(interval);
    }, []);

    const handleLogout = () => {
        authService.logout();
        setIsAuthenticated(false);
        setCurrentUser(null);
        navigate('/login');
    };

    return (
        <header className="App-header">
            <h1>Timesheet Cloud</h1>
            <nav>
                <ul>
                    {isAuthenticated ? (
                        <>
                            <li><Link to="/timesheets">Timesheets</Link></li>
                            <li><Link to="/">Dashboard</Link></li>
                            <li>
                                <span style={{ marginRight: '10px' }}>
                                    Welcome, {currentUser?.name}!
                                </span>
                                <button 
                                    onClick={handleLogout}
                                    style={{ 
                                        padding: '4px 8px', 
                                        backgroundColor: '#dc3545', 
                                        color: 'white', 
                                        border: 'none', 
                                        borderRadius: '4px', 
                                        cursor: 'pointer' 
                                    }}
                                >
                                    Logout
                                </button>
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

function App() {
    return (
        <Router>
            <div className="App">
                <Navigation />
                <main>
                    <Routes>
                        <Route path="/" element={<Dashboard />} />
                        <Route path="/timesheets" element={<Timesheets />} />
                        <Route path="/login" element={<Login />} />
                        <Route path="/signup" element={<Signup />} />
                    </Routes>
                </main>
                <footer>
                    <p>&copy; 2025 Timesheet Cloud. All rights reserved.</p>
                </footer>
            </div>
        </Router>
    );
}

export default App;