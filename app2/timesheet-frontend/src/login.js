// src/LoginPage.js
import React, { useState } from "react";

function LoginPage() {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [message, setMessage] = useState("");

    const handleLogin = async (e) => {
        e.preventDefault();

        try {
            const response = await fetch("http://localhost:8080/auth/login", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ email, password }),
            });

            if (response.ok) {
                setMessage("Login successful!");
            } else {
                setMessage("Invalid credentials");
            }
        } catch (error) {
            setMessage("Error connecting to server");
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
                />
                <input
                    type="password"
                    placeholder="Password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    style={{ width: "100%", marginBottom: "10px", padding: "8px" }}
                />
                <button type="submit" style={{ width: "100%", padding: "8px" }}>
                    Login
                </button>
                <p>{message}</p>
            </form>
        </div>
    );
}

export default LoginPage;
