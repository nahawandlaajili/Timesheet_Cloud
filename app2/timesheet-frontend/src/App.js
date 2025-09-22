import React from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import './App.css';
import Dashboard from './Dashboard';
import Login from './login';
import Timesheets from './timesheets_component';

function App() {
    return (
        <Router>
            <div className="App">
                <header className="App-header">
                    <h1>Timesheet Cloud</h1>
                    <nav>
                        <ul>
                            <li><Link to="/">Dashboard</Link></li>
                            <li><Link to="/timesheets">Timesheets</Link></li>
                            <li><Link to="/login">Login</Link></li>
                        </ul>
                    </nav>
                </header>
                <main>
                    <Routes>
                        <Route path="/" element={<Dashboard />} />
                        <Route path="/timesheets" element={<Timesheets />} />
                        <Route path="/login" element={<Login />} />
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