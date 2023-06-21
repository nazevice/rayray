import React, { useEffect, useState } from "react";
import { Box, TextField, Button, Grid } from '@mui/material';
import FormModal from "../FormModal/FormModal";
import StudyClassCard from "../ItemCard/StudyClassCard";
import LectureCard from "../ItemCard/LectureCard";

const Lecture = () => {
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [open, setOpen] = React.useState(false);
  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await fetch('http://localhost:9090/lectures');
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
      lectureName: value.lectureName,
      moduleName: value.moduleName,
      duration: value.duration,
      lectureDate: value.lectureDate,
      course: value.course,
    };
    // Send the form data to your backend server
    fetch('http://localhost:9090/lectures', {
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
          label="Name der Lehrveranstaltung"
          name="lectureName"
          margin="normal"
        />
        <TextField
          fullWidth
          label="Name des Moduls"
          name="moduleName"
          margin="normal"
        />
        <TextField
          fullWidth
          label="Vorlesungszeit"
          name="duration"
          type="time"
          margin="normal"
          />
        <TextField
          fullWidth
          label="Datum der Vorlesung"
          name="lectureDate"
          type="date"
          margin="normal"
          />
        <TextField
          fullWidth
          label="Studiengang"
          name="course"
          margin="normal"
          />
      </FormModal>
      {data && (
        <Grid container spacing={2}>
          {data.map(item => (
            <Grid item xs={4}>
              <LectureCard
                key={item.id}
                lecture={item}
                handleOpen={handleOpen}
              />
            </Grid>
          ))}
        </Grid>
      )}
    </Box>
  );
};

export default Lecture;
