import React, { useEffect, useState } from "react";
import { Box, TextField, Grid, Typography, Autocomplete } from '@mui/material';
import FormModal from "../FormModal/FormModal";
import LecturersCard from "../ItemCard/LecturersCard";
import ContentContainer from "../ContentContainer/ContentContainer";

const Dozent = () => {
  const [lecturers, setLecturers] = useState([]);
  const [lectures, setLectures] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [open, setOpen] = useState(false)
  const [title, setTitle] = useState('');
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [mail, setMail] = useState('')
  const [selectedOptions, setSelectedOptions] = useState([]);
  const handleClose = () => setOpen(false);

  useEffect(() => {
    fetchData('http://localhost:9090/lecturers', setLecturers);
    fetchData('http://localhost:9090/lectures', setLectures)
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

  const handleOpen = (lecturer) => {
    console.log(lecturer)
    if(lecturer!=null) {
      setFirstName(lecturer.firstName);
      setLastName(lecturer.lastName);
      setMail(lecturer.email);
      setTitle("Dozent ändern")
    } else {
      setFirstName('');
      setLastName('');
      setMail('');
      setTitle("Dozent hinzufügen")
    }
    setOpen(true)
  }

  const handleSelectionChange = (event, value) => {
    setSelectedOptions(value);
  };

  const handleSubmit = (event) => {
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
    <ContentContainer handleOpen={handleOpen}>
      <FormModal open={open} handleClose={handleClose} handleSubmit={handleSubmit}>
        <Typography>{title}</Typography>
        <TextField 
          label="Vorname" 
          name="firstName" 
          margin="normal"
          value={firstName}
          onChange={(event) => setFirstName(event.target.value)}
          fullWidth 
          />
        <TextField 
          label="Nachname" 
          name="lastName" 
          margin="normal"
          value={lastName}
          onChange={(event) => setLastName(event.target.value)}
          fullWidth 
        />
        <TextField 
          label="E-Mail" 
          name="email" 
          type="email"
          margin="normal"
          value={mail}
          onChange={(event) => setMail(event.target.value)}
          fullWidth 
        />
        <Autocomplete
      multiple
      options={lectures} // Replace with your options array
      getOptionLabel={(lectures) => lectures.lectureName} // Replace with the label property of your option object
      value={selectedOptions}
      onChange={handleSelectionChange}
      renderInput={(params) => (
        <TextField {...params} label="Lehrveranstaltung" margin="normal"/>
      )}
    />
      </FormModal>
      {lecturers.length !== 0 && (
        <Grid container spacing={2}>
          {lecturers.map(item => (
            <Grid item xs={4}>
              <LecturersCard
                key={item.id}
                lecturer={item}
                handleOpen={handleOpen}
              />
            </Grid>
          ))}
        </Grid>
      )}
    </ContentContainer>
  )
}

export default Dozent;
