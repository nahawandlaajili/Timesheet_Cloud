import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import LoginPage from "./Login";
import Dashboard from "./Dashboard";
// import Timesheet from "./Timesheet";
// import Vacation from "./Vacation";
// import Sick from "./Sick";

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/login" element={<LoginPage />} />
                <Route path="/" element={<Dashboard />} />
                {/* <Route path="/Timesheet" element={<Timesheet />} />
                <Route path="/Vacation" element={<Vacation />} />
                <Route path="/Sick" element={<Sick />} /> */}
            </Routes>
        </Router>
    );
}

export default App;