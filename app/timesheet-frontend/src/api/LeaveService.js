import axios from "axios";

const API_BASE_URL = "https://localhost:8082/api/leaves";

export const requestLeave = (userId, startDate, endDate) =>
  axios.post(`${API_BASE_URL}/request`, { userId, startDate, endDate },

{
    withCredentials: true  // if you need cookies/auth
}
  );

export const getUserLeaves = (userId) =>
  axios.get(`${API_BASE_URL}/user/${userId}`);

export const getAllLeaves = () => axios.get(API_BASE_URL);

export const approveLeave = (leaveId) =>
  axios.put(`${API_BASE_URL}/${leaveId}/approve`);

export const rejectLeave = (leaveId) =>
  axios.put(`${API_BASE_URL}/${leaveId}/reject`);
