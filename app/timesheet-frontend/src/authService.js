// Authentication service for managing JWT tokens and API calls
class AuthService {
    constructor() {
        this.baseURL = 'http://localhost:7070'; // User service URL
        this.timesheetURL = 'http://localhost:7071'; // Timesheet service URL
        this.leaveURL = 'http://localhost:7072'; // Leave service URL
    }

    // Store token in localStorage
    setToken(token) {
        localStorage.setItem('jwt_token', token);
    }

    // Get token from localStorage
    getToken() {
        return localStorage.getItem('jwt_token');
    }

    // Remove token from localStorage
    removeToken() {
        localStorage.removeItem('jwt_token');
    }

    // Check if user is authenticated
    isAuthenticated() {
        const token = this.getToken();
        if (!token) return false;
        
        try {
            // Basic JWT expiration check (decode payload)
            const payload = JSON.parse(atob(token.split('.')[1]));
            const currentTime = Date.now() / 1000;
            return payload.exp > currentTime;
        } catch (error) {
            return false;
        }
    }

    // Get current user info from token
    getCurrentUser() {
        const token = this.getToken();
        if (!token) return null;
        
        try {
            const payload = JSON.parse(atob(token.split('.')[1]));
            return {
                email: payload.email,
                userId: payload.userId,
                name: payload.name
            };
        } catch (error) {
            return null;
        }
    }

    // Login user
    async login(email, password) {
        const response = await fetch(`${this.baseURL}/auth/login`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ email, password })
        });

        if (!response.ok) {
            throw new Error('Invalid credentials');
        }

        const data = await response.json();
        this.setToken(data.token);
        return data;
    }

    // Signup user
    async signup(name, email, password) {
        const response = await fetch(`${this.baseURL}/auth/signup`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ name, email, password })
        });

        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(errorData.message || 'Failed to create account');
        }

        return await response.json();
    }

    // Logout user
    logout() {
        this.removeToken();
    }

    // Make authenticated API call to timesheet service
    async apiCall(endpoint, options = {}) {
        const token = this.getToken();
        if (!token) {
            throw new Error('No authentication token found');
        }

        const config = {
            ...options,
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`,
                ...options.headers
            }
        };

        const response = await fetch(`${this.timesheetURL}${endpoint}`, config);
        
        if (response.status === 401 || response.status === 403) {
            this.removeToken();
            throw new Error('Authentication failed. Please login again.');
        }

        if (!response.ok) {
            const errorText = await response.text();
            console.error('API Error:', errorText);
            throw new Error(`API call failed: ${response.status} ${response.statusText} - ${errorText}`);
        }

        return await response.json();
    }

    // Test connectivity without auth
    async testConnection() {
        try {
            // Try the public endpoint first
            const response = await fetch(`${this.timesheetURL}/public/health`);
            if (response.ok) {
                return await response.text();
            }
            
            // Fallback to original health endpoint
            const fallbackResponse = await fetch(`${this.timesheetURL}/timesheets/health`);
            return await fallbackResponse.text();
        } catch (error) {
            throw new Error(`Connection test failed: ${error.message}`);
        }
    }

    // Timesheet-specific API methods
    async getTimesheets() {
        return this.apiCall('/timesheets');
    }

    async createTimesheet(timesheetData) {
        return this.apiCall('/timesheets', {
            method: 'POST',
            body: JSON.stringify(timesheetData)
        });
    }

    async getUserTimesheets(userId) {
        return this.apiCall(`/timesheets/user/${userId}`);
    }
}

// Create and export a singleton instance
const authService = new AuthService();
export default authService;
