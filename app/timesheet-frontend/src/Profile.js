import React from 'react';

function Profile({ user }) {
  if (!user) {
    return <p>Please log in to view your profile.</p>;
  }

  return (
    <div className="profile-container">
      <h2>My Profile</h2>
      <p><strong>Name:</strong> {user.name}</p>
      <p><strong>Email:</strong> {user.email}</p>
      <p><strong>Department:</strong> {user.department || 'N/A'}</p>
      <p><strong>Role:</strong> {user.role || 'Employee'}</p>
    </div>
  );
}

export default Profile;