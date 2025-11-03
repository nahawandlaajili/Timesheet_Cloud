import React, { useState, useEffect } from "react";
import { useNavigate, Link } from "react-router-dom";
import authService from "./authService";

function LoginPage() {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [message, setMessage] = useState("");
    const [loading, setLoading] = useState(false);
    const navigate = useNavigate();

    useEffect(() => {
        // Redirect if already authenticated
        if (authService.isAuthenticated()) {
            navigate("/timesheets");
        }
    }, [navigate]);

    const handleLogin = async (e) => {
        e.preventDefault();
        setLoading(true);
        setMessage("");

        try {
            await authService.login(email, password);
                setMessage("Login successful!");
            navigate("/timesheets");
        } catch (error) {
            setMessage(error.message || "Login failed");
        } finally {
            setLoading(false);
        }
    };

    return (
        <div style={{ display: "flex", justifyContent: "center", marginTop: "100px" }}>
            <form onSubmit={handleLogin} style={{ width: "300px" }}>
                <h2>Login</h2>
                
                <input
                    type="email"
                    placeholder="Email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    style={{ width: "100%", marginBottom: "10px", padding: "8px" }}
                    required
                />
                
                <input
                    type="password"
                    placeholder="Password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    style={{ width: "100%", marginBottom: "10px", padding: "8px" }}
                    required
                />
                
                <button 
                    type="submit" 
                    style={{ width: "100%", padding: "8px" }}
                    disabled={loading}
                >
                    {loading ? "Logging in..." : "Login"}
                </button>
                
                <p style={{ textAlign: "center", marginTop: "15px" }}>
                    Don't have an account? <Link to="/signup">Sign up here</Link>
                </p>
                
                {message && (
                    <p style={{ 
                        color: message.includes("successful") ? "green" : "red",
                        textAlign: "center"
                    }}>
                        {message}
                    </p>
                )}
            </form>
        </div>
    );
}

export default LoginPage;
