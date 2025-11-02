import React from "react";
import LeaveRequestForm from "./components/LeaveRequestForm";
import LeaveList from "./components/LeaveList";
import AdminLeaveList from "./components/AdminLeaveList";

export default function App() {
  const userId = 1; // simulate logged-in user

  return (
    <div className="min-h-screen bg-gray-100 p-8">
      <h1 className="text-3xl font-bold text-center text-blue-700 mb-6">
        Leave Management System
      </h1>
      <LeaveRequestForm userId={userId} />
      <LeaveList userId={userId} />
      <AdminLeaveList />
    </div>
  );
}