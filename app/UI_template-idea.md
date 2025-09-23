1. Login 
Fields: Email, Password

Button: Login

On success → redirect to Dashboard

2. Dashboard contains icons 
Top bar: User Profile, Logout

Sidebar menu:

My Timesheet

Vacation Requests

Leave Requests

Reports

3. Timesheet
Calendar or weekly view

Table:

Date	Project / Task	Hours Worked	Status
2025-09-01	Project A	8	Submitted
2025-09-02	Project B	6	Draft

Buttons:

Add Entry

Submit Week

4. Vacation Requests

Form:

Start Date

End Date

Reason

Table of requests:

Request ID	Dates	Status
12	Sep 10–Sep 15	Approved
13	Oct 01–Oct 05	Pending


5. Leave Requests

(Similar to vacation, but for sick/other leaves)

Form + History Table



6. Reports (optional, admin role)

Total hours worked (per week/month)

Absences report

Export as CSV/PDF



LoginPage.js

Dashboard.js

TimesheetPage.js

VacationPage.js

LeavePage.js

Use React Router for navigation

Axios/Fetch for API calls (http://localhost:8081/timesheet, etc.)