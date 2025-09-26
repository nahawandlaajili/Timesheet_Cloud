import React, { useState } from "react";
import { useNavigate, Link } from "react-router-dom";

function SignupPage() {
    const [formData, setFormData] = useState({
        name: "",
        email: "",
        password: ""
    });
    const [message, setMessage] = useState("");
    const [loading, setLoading] = useState(false);
    const navigate = useNavigate();

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setFormData({
            ...formData,
            [name]: value
        });
    };

    const handleSignup = async (e) => {
        e.preventDefault();
        setLoading(true);
        setMessage("");

        try {
            const response = await fetch("http://localhost:7070/auth/signup", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(formData),
            });

            if (response.ok) {
                const userData = await response.json();
                setMessage("Account created successfully! Redirecting to login...");
                setTimeout(() => {
                    navigate("/login");
                }, 2000);
            } else {
                const errorData = await response.json();
                setMessage(errorData.message || "Failed to create account");
            }
        } catch (error) {
            setMessage("Error connecting to server");
        } finally {
            setLoading(false);
        }
    };

    return (
        <div style={{ display: "flex", justifyContent: "center", marginTop: "100px" }}>
            <form onSubmit={handleSignup} style={{ width: "300px" }}>
                <h2>Sign Up</h2>
                
                <input
                    type="text"
                    name="name"
                    placeholder="Full Name"
                    value={formData.name}
                    onChange={handleInputChange}
                    style={{ width: "100%", marginBottom: "10px", padding: "8px" }}
                    required
                />
                
                <input
                    type="email"
                    name="email"
                    placeholder="Email"
                    value={formData.email}
                    onChange={handleInputChange}
                    style={{ width: "100%", marginBottom: "10px", padding: "8px" }}
                    required
                />
                
                <input
                    type="password"
                    name="password"
                    placeholder="Password"
                    value={formData.password}
                    onChange={handleInputChange}
                    style={{ width: "100%", marginBottom: "10px", padding: "8px" }}
                    required
                    minLength="6"
                />
                
                <button 
                    type="submit" 
                    style={{ width: "100%", padding: "8px" }}
                    disabled={loading}
                >
                    {loading ? "Creating Account..." : "Sign Up"}
                </button>
                
                <p style={{ textAlign: "center", marginTop: "15px" }}>
                    Already have an account? <Link to="/login">Login here</Link>
                </p>
                
                {message && (
                    <p style={{ 
                        color: message.includes("successfully") ? "green" : "red",
                        textAlign: "center"
                    }}>
                        {message}
                    </p>
                )}
            </form>
        </div>
    );
}

export default SignupPage;
