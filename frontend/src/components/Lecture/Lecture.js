import React from "react";
import { Box, TextField, Button } from '@mui/material';

const Lecture = () => {
  const handleSubmit = (event) => {
    event.preventDefault();
    // Extract the form data
    const data = new FormData(event.target);
    // Convert the form data to an object
    const value = Object.fromEntries(data.entries());
    // Prepare the payload
    const payload = {
      lectureName: value.lectureName,
      modulName: value.modulName,
      duration: value.duration,
      // add any other fields as needed
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
    <Box component="form" onSubmit={handleSubmit} sx={{ width: 'md', p: 2 }}>
      <TextField label="Name der Lehrveranstaltung" name="lectureName" fullWidth />
      <TextField label="Name des Moduls" name="modulName" fullWidth />
      <TextField label="Vorlesungszeit" name="duration" type="time" fullWidth />
      <TextField label="Datum der Vorlesung" name="lectureDate" type="date" fullWidth />
      <TextField label="Studiengang" name="course" fullWidth />
      <Button type="submit" variant="contained" color="primary">Submit</Button>
    </Box>
  );
};

export default Lecture;
