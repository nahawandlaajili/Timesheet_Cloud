import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import {
    Box,
    Container,
    Grid,
    Paper,
    Typography,
    Card,
    CardContent,
    CircularProgress,
    Alert,
    Chip,
    Avatar
} from '@mui/material';
import {
    AccessTime,
    CalendarToday,
    TrendingUp,
    WorkOutline
} from '@mui/icons-material';
import { LineChart, BarChart, PieChart } from '@mui/x-charts';
import { format, parseISO, startOfWeek, endOfWeek, eachDayOfInterval, isWithinInterval } from 'date-fns';
import authService from './authService';

function Dashboard() {
    const [timesheets, setTimesheets] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [currentUser, setCurrentUser] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        // Check authentication
        if (!authService.isAuthenticated()) {
            navigate('/login');
            return;
        }

        const user = authService.getCurrentUser();
        setCurrentUser(user);
        loadDashboardData();
    }, [navigate]);

    const loadDashboardData = async () => {
        try {
            setLoading(true);
            const data = await authService.getDashboardData();
            setTimesheets(data);
            setError(null);
        } catch (err) {
            console.error('Error loading dashboard data:', err);
            if (err.message.includes('Authentication failed')) {
                navigate('/login');
            } else {
                setError('Failed to load dashboard data. Please try again.');
            }
        } finally {
            setLoading(false);
        }
    };

    // Calculate dashboard statistics
    const calculateStats = () => {
        const totalHours = timesheets.reduce((sum, ts) => sum + (ts.hoursWorked || 0), 0);
        const totalDays = timesheets.length;
        const avgHoursPerDay = totalDays > 0 ? (totalHours / totalDays).toFixed(1) : 0;
        
        // Current week data
        const now = new Date();
        const weekStart = startOfWeek(now);
        const weekEnd = endOfWeek(now);
        
        const thisWeekTimesheets = timesheets.filter(ts => {
            const tsDate = parseISO(ts.date);
            return isWithinInterval(tsDate, { start: weekStart, end: weekEnd });
        });
        
        const thisWeekHours = thisWeekTimesheets.reduce((sum, ts) => sum + (ts.hoursWorked || 0), 0);

        return {
            totalHours,
            totalDays,
            avgHoursPerDay,
            thisWeekHours
        };
    };

    // Prepare chart data
    const prepareChartData = () => {
        // Sort timesheets by date
        const sortedTimesheets = [...timesheets].sort((a, b) => new Date(a.date) - new Date(b.date));
        
        // Line chart data (hours over time)
        const lineChartData = sortedTimesheets.map(ts => ({
            date: format(parseISO(ts.date), 'MMM dd'),
            hours: ts.hoursWorked || 0
        }));

        // Bar chart data (daily hours for last 7 entries)
        const last7Days = sortedTimesheets.slice(-7);
        const barChartData = last7Days.map(ts => ({
            day: format(parseISO(ts.date), 'EEE'),
            hours: ts.hoursWorked || 0
        }));

        // Pie chart data (hours distribution by description)
        const hoursByDescription = {};
        timesheets.forEach(ts => {
            const desc = ts.description || 'No Description';
            hoursByDescription[desc] = (hoursByDescription[desc] || 0) + (ts.hoursWorked || 0);
        });
        
        const pieChartData = Object.entries(hoursByDescription).map(([desc, hours]) => ({
            label: desc.length > 20 ? desc.substring(0, 20) + '...' : desc,
            value: hours
        }));

        return { lineChartData, barChartData, pieChartData };
    };

    if (loading) {
        return (
            <Container maxWidth="lg" sx={{ mt: 4, mb: 4 }}>
                <Box display="flex" justifyContent="center" alignItems="center" minHeight="400px">
                    <CircularProgress />
                </Box>
            </Container>
        );
    }

    if (error) {
        return (
            <Container maxWidth="lg" sx={{ mt: 4, mb: 4 }}>
                <Alert severity="error">{error}</Alert>
            </Container>
        );
    }

    const stats = calculateStats();
    const { lineChartData, barChartData, pieChartData } = prepareChartData();

    return (
        <Container maxWidth="lg" sx={{ mt: 4, mb: 4 }}>
            {/* Header */}
            <Box mb={4}>
                <Typography variant="h4" component="h1" gutterBottom>
                    Dashboard
                </Typography>
                {currentUser && (
                    <Box display="flex" alignItems="center" gap={2}>
                        <Avatar sx={{ bgcolor: 'primary.main' }}>
                            {currentUser.name?.charAt(0).toUpperCase()}
                        </Avatar>
                        <Typography variant="h6" color="text.secondary">
                            Welcome back, {currentUser.name}!
                        </Typography>
                    </Box>
                )}
            </Box>

            {/* Statistics Cards */}
            <Grid container spacing={3} mb={4}>
                <Grid item xs={12} sm={6} md={3}>
                    <Card>
                        <CardContent>
                            <Box display="flex" alignItems="center" gap={2}>
                                <AccessTime color="primary" />
                                <Box>
                                    <Typography color="text.secondary" gutterBottom>
                                        Total Hours
                                    </Typography>
                                    <Typography variant="h5">
                                        {stats.totalHours}h
                                    </Typography>
                                </Box>
                            </Box>
                        </CardContent>
                    </Card>
                </Grid>

                <Grid item xs={12} sm={6} md={3}>
                    <Card>
                        <CardContent>
                            <Box display="flex" alignItems="center" gap={2}>
                                <CalendarToday color="primary" />
                                <Box>
                                    <Typography color="text.secondary" gutterBottom>
                                        Total Days
                                    </Typography>
                                    <Typography variant="h5">
                                        {stats.totalDays}
                                    </Typography>
                                </Box>
                            </Box>
                        </CardContent>
                    </Card>
                </Grid>

                <Grid item xs={12} sm={6} md={3}>
                    <Card>
                        <CardContent>
                            <Box display="flex" alignItems="center" gap={2}>
                                <TrendingUp color="primary" />
                                <Box>
                                    <Typography color="text.secondary" gutterBottom>
                                        Avg Hours/Day
                                    </Typography>
                                    <Typography variant="h5">
                                        {stats.avgHoursPerDay}h
                                    </Typography>
                                </Box>
                            </Box>
                        </CardContent>
                    </Card>
                </Grid>

                <Grid item xs={12} sm={6} md={3}>
                    <Card>
                        <CardContent>
                            <Box display="flex" alignItems="center" gap={2}>
                                <WorkOutline color="primary" />
                                <Box>
                                    <Typography color="text.secondary" gutterBottom>
                                        This Week
                                    </Typography>
                                    <Typography variant="h5">
                                        {stats.thisWeekHours}h
                                    </Typography>
                                </Box>
                            </Box>
                        </CardContent>
                    </Card>
                </Grid>
            </Grid>

            {/* Charts */}
            {timesheets.length > 0 ? (
                <Grid container spacing={3}>
                    {/* Hours Over Time - Line Chart */}
                    <Grid item xs={12} md={8}>
                        <Paper sx={{ p: 3 }}>
                            <Typography variant="h6" gutterBottom>
                                Hours Worked Over Time
                            </Typography>
                            {lineChartData.length > 0 && (
                                <LineChart
                                    width={600}
                                    height={300}
                                    series={[
                                        {
                                            data: lineChartData.map(d => d.hours),
                                            label: 'Hours Worked',
                                            color: '#1976d2'
                                        }
                                    ]}
                                    xAxis={[
                                        {
                                            scaleType: 'point',
                                            data: lineChartData.map(d => d.date)
                                        }
                                    ]}
                                />
                            )}
                        </Paper>
                    </Grid>

                    {/* Hours by Task - Pie Chart */}
                    <Grid item xs={12} md={4}>
                        <Paper sx={{ p: 3 }}>
                            <Typography variant="h6" gutterBottom>
                                Hours by Task
                            </Typography>
                            {pieChartData.length > 0 && (
                                <PieChart
                                    series={[
                                        {
                                            data: pieChartData,
                                            highlightScope: { faded: 'global', highlighted: 'item' },
                                        }
                                    ]}
                                    width={300}
                                    height={200}
                                />
                            )}
                        </Paper>
                    </Grid>

                    {/* Recent Days - Bar Chart */}
                    <Grid item xs={12}>
                        <Paper sx={{ p: 3 }}>
                            <Typography variant="h6" gutterBottom>
                                Recent Days Activity
                            </Typography>
                            {barChartData.length > 0 && (
                                <BarChart
                                    width={800}
                                    height={300}
                                    series={[
                                        {
                                            data: barChartData.map(d => d.hours),
                                            label: 'Hours Worked',
                                            color: '#2e7d32'
                                        }
                                    ]}
                                    xAxis={[
                                        {
                                            scaleType: 'band',
                                            data: barChartData.map(d => d.day)
                                        }
                                    ]}
                                />
                            )}
                        </Paper>
                    </Grid>
                </Grid>
            ) : (
                <Paper sx={{ p: 4, textAlign: 'center' }}>
                    <Typography variant="h6" color="text.secondary" gutterBottom>
                        No timesheet data available
                    </Typography>
                    <Typography color="text.secondary" mb={2}>
                        Start by adding some timesheets to see your dashboard analytics.
                    </Typography>
                    <Chip 
                        label="Go to Timesheets" 
                        onClick={() => navigate('/timesheets')}
                        color="primary"
                        variant="outlined"
                    />
                </Paper>
            )}
        </Container>
    );
}

export default Dashboard;
