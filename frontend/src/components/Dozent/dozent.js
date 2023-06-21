import React, { useEffect, useState } from "react";
import { Box, TextField, Button, Grid } from '@mui/material';
import FormModal from "../FormModal/FormModal";
import StudyProgramCard from "../ItemCard/StudyProgramCard";
import LecturersCard from "../ItemCard/LecturersCard";

const Dozent = () => {
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [open, setOpen] = React.useState(false);
  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await fetch('http://localhost:9090/lecturers');
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

    // Ensure that the keys match the expected format of the backend
    const payload = {
      firstName: value.firstName,
      lastName: value.lastName,
      email: value.email,
    };

    // Send the form data to our backend server
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
    <Box fill padding={2}>
      <FormModal open={open} handleClose={handleClose} handleSubmit={handleSubmit}>
      <TextField label="Vorname" name="firstName" fullWidth />
      <TextField label="Nachname" name="lastName" fullWidth />
      <TextField label="E-Mail" name="email" type="email" fullWidth />
      <Button type="submit" variant="contained" color="primary">Submit</Button>
    </FormModal>
    {data && (
                <Grid container spacing={2}>
                    {data.map(item => (
                        <Grid item xs={4}>
                          <LecturersCard 
                            key={item.id}
                            firstName={item.firstName}
                            lastName={item.lastName}
                            mail={item.email}
                          />
                            <StudyProgramCard
                                key={item.id}
                                shortName={item.shortName}
                                programName={item.name}
                                handleOpen={handleOpen}
                            />
                        </Grid>
                    ))}
                </Grid>
            )}
        </Box>
    )
}

export default Dozent;
