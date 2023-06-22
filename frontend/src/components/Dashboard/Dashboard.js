import React, { useEffect, useState } from 'react';
import ContentContainer from '../ContentContainer/ContentContainer';
import { Box, Card, CardContent, Grid, Typography } from '@mui/material';
import LecturersCard from '../ItemCard/LecturersCard';
import { DateCalendar } from '@mui/x-date-pickers';

const Dashboard = () => {
    const [lecturers, setLecturers] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [date, setDate] = useState()
    const [lectureDates, setLectureDates] = useState([]);
    const [lecturesInRange, setLecturesInRange] = useState([]);

    useEffect(() => {
        fetchData('http://localhost:9090/lecturers', setLecturers);
        fetchData('http://localhost:9090/lecturedates', setLectureDates);
    }, []);

    const fetchData = async (url, setData) => {
        try {
            const response = await fetch(url);
            if (!response.ok) {
                throw new Error('Request failed');
            }
            const jsonData = await response.json();
            setData(jsonData);
        } catch (error) {
            setError(error.message);
        } finally {
            setLoading(false);
        }
    };

    if (loading) {
        return <div>Loading...</div>;
    }

    if (error) {
        return <div>Error: {error}</div>;
    }

    function generateDateRange(startDateArr, endDateArr) {
        console.log("gen: " + date);
        const startDate = new Date(...startDateArr);
        const endDate = new Date(...endDateArr);
        if(date===undefined) return false;
        if(date.isBetween(startDate, endDate)){
            return true;
        } else {
            return false;
        }
    }

    const onDateChange = (date) => {
        setDate(date);
        console.log("date change" + date)
        let currentLectureDate = []
        if (lectureDates.length !== 0 && date !== undefined) {
            for (let i = 0; i < lectureDates.length; i++) {
                if(generateDateRange(lectureDates[i].startDate, lectureDates[i].endDate)) {
                    currentLectureDate.push(lectureDates[i]);
                }
            }
            setLecturesInRange(currentLectureDate)
        }
    }

    return (
        <ContentContainer>
            <Grid container spacing={2}>
                <Grid item xs={12}>
                    <Box
                        display="flex"
                        alignItems="center"
                        gap={2}
                        style={{ overflowX: 'auto' }}
                        paddingBottom={2}
                    >
                        {lecturers.length !== 0 &&
                            lecturers.map((item) => (
                                <LecturersCard key={item.id} lecturer={item} />
                            ))}
                    </Box>
                </Grid>
                <Grid item xs={6}>
                    <Box>
                        <Typography>Datum</Typography>
                        <DateCalendar
                            value={date}
                            onChange={(newDate) => onDateChange(newDate)}
                            slotProps={{
                                day: {
                                    selectedDay: date,
                                },
                            }}
                        />
                    </Box>
                </Grid>
                <Grid item xs={6}>
                    <Typography>Vorlesungen</Typography>
                    {lecturesInRange.length !== 0 && 
                        lecturesInRange.map(item => (
                            <Card>
                                <CardContent>
                                    <Typography>{"Von: " + item.startDate + " Bis: " + item.endDate}</Typography>
                                </CardContent>
                            </Card>
                        ))
                    }
                </Grid>
            </Grid>
        </ContentContainer>
    )
}
export default Dashboard;
