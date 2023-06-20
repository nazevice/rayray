import React from "react";
import { Box, TextField, Button } from '@mui/material';

const Dozent = () => {
  const handleSubmit = (event) => {
    event.preventDefault();
  
    // Extract the form data
    const data = new FormData(event.target);
  
    // Convert the form data to an object
    const value = Object.fromEntries(data.entries());
  
    // Ensure that the keys match the expected format of the backend
    const payload = {
      firstName: value.firstName,
      lastName: value.lastName,
      email: value.email,
    };
  
    // Send the form data to your backend server
    fetch('http://localhost:9090/lecturers', {
      method: 'POST',
      body: JSON.stringify(payload),
      headers: {
        'Content-Type': 'application/json',
      },
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
      <TextField label="Vorname" name="firstName" fullWidth />
      <TextField label="Nachname" name="lastName" fullWidth />
      <TextField label="E-Mail" name="email" type="email" fullWidth />
      <Button type="submit" variant="contained" color="primary">Submit</Button>
    </Box>
  );
};

export default Dozent;
