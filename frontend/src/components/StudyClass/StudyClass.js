import { Box, Grid, TextField, Button } from "@mui/material";
import React, { useEffect, useState } from "react";
import FormModal from "../FormModal/FormModal";
import LecturersCard from "../ItemCard/LecturersCard";
import StudyClassCard from "../ItemCard/StudyClassCard";

const StudyClass = () => {
    const [data, setData] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [open, setOpen] = React.useState(false);
    const handleOpen = () => setOpen(true);
    const handleClose = () => setOpen(false);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await fetch('http://localhost:9090/studyclasses');
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

        fetchData();
    }, []);

    if (loading) {
        return <div>Loading...</div>;
    }

    if (error) {
        return <div>Error: {error}</div>;
    }

    const handleSubmit = (event) => {
        event.preventDefault();
        // Extract the form data
        const data = new FormData(event.target);
        // Convert the form data to an object
        const value = Object.fromEntries(data.entries());
        // Prepare the payload
        const payload = {
            className: value.className,
            startDate: value.startDate,
            endDate: value.endDate,
        };
        // Send the form data to your backend server
        fetch('http://localhost:9090/studyclasses', {
            method: 'POST',
            body: JSON.stringify(payload),
            headers: {
                'Content-Type': 'application/json'
            }
        }).then((response) => {
            if (response.ok) {
                // Handle successful submission
                console.log('Data submitted successfully');
            } else {
                // Handle errors
                console.error('Error submitting data:', response);
            }
        });
    };

    return (
        <Box fill padding={2}>
            <FormModal open={open} handleClose={handleClose} handleSubmit={handleSubmit}>
          <TextField
            fullWidth
            label="Name der Studienklasse"
            name="className"
            margin="normal"
            />
          <TextField
            fullWidth
            label="Beginn der Studienklasse"
            name="startDate"
            type="date"
            margin="normal"
            />
          <TextField
            fullWidth
            label="Ende der Studienklasse"
            name="endDate"
            type="date"
            margin="normal"
          />
            </FormModal>
            {data && (
        <Grid container spacing={2}>
          {data.map(item => (
            <Grid item xs={4}>
            <StudyClassCard 
                key={item.id}
                item={item}
                handleOpen={handleOpen}
            />
            </Grid>
          ))}
        </Grid>
      )}
        </Box>
    )
}
export default StudyClass;
