import React, { useEffect, useState } from "react";
import { Box, TextField, Grid, Typography, Autocomplete, Snackbar } from '@mui/material';
import MuiAlert from '@mui/material/Alert';
import FormModal from "../FormModal/FormModal";
import LecturersCard from "../ItemCard/LecturersCard";
import ContentContainer from "../ContentContainer/ContentContainer";



const Dozent = () => {
  const [lecturerId, setLecturerId] = useState('');
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

  const [refreshKey, setRefreshKey] = useState(0);
  useEffect(() => {
    fetchData('http://localhost:9090/lecturers', setLecturers);
    fetchData('http://localhost:9090/lectures', setLectures)
  }, [refreshKey]);

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
    if (lecturer != null) {
      setLecturerId(lecturer.id); // set the lecturerId state here
      setFirstName(lecturer.firstName);
      setLastName(lecturer.lastName);
      setMail(lecturer.email);
      setTitle("Dozent ändern");
    } else {
      setLecturerId(''); // reset the lecturerId state here
      setFirstName('');
      setLastName('');
      setMail('');
      setTitle("Dozent hinzufügen");
    }
    setOpen(true);
  }


  const handleSelectionChange = (event, value) => {
    setSelectedOptions(value);
  };

  const handleSubmit = (event) => {
    event.preventDefault();

    // Extract the form data
    const data = new FormData(event.target);

    // Convert the form data to an object
    const value = Object.fromEntries(data.entries());

    // Start creating the payload with the form data
    const payload = {
      firstName: value.firstName,
      lastName: value.lastName,
      email: value.email,
    };

    // If this is an update operation, include the id and the selected lectures in the payload
    if (title === "Dozent ändern") {
      payload.id = lecturerId; // Use lecturerId here
      const selectedLectures = selectedOptions.map((lecture) => ({ id: lecture.id }));
      payload.lectures = selectedLectures;
    }

    // Determine the appropriate method and URL for the operation
    const method = (title === "Dozent ändern") ? 'PUT' : 'POST';
    const url = 'http://localhost:9090/lecturers';

    // Send the form data to our backend server
    fetch(url, {
      method: method,
      body: JSON.stringify(payload),
      headers: {
        'Content-Type': 'application/json',
      },
    }).then((response) => {
      if (response.ok) {
        // Handle successful submission
        console.log('Data submitted successfully');
        setRefreshKey(oldKey => oldKey + 1); // trigger a refetch
      } else {
        // Handle errors
        console.error('Error submitting data:', response);
      }
    });
  };

  const handleDelete = (id) => {
    // Optimistically remove the lecturer from the local state
    setLecturers(prevLecturers => prevLecturers.filter(lecturer => lecturer.id !== id));
    
    fetch(`http://localhost:9090/lecturers/${id}`, {
      method: 'DELETE',
    })
      .then(response => {
        if (!response.ok) {
          throw new Error(`HTTP error! status: ${response.status}`);
        }
        return response.json();
      })
      .then(data => {
        console.log(data);
      })
      .catch(error => {
        console.log('Error deleting the lecturer: ', error);
        // If the delete operation failed, refetch the data
        setRefreshKey(oldKey => oldKey + 1);
      });
  }

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
          options={lectures}
          getOptionLabel={(lectures) => lectures.lectureName}
          value={selectedOptions}
          onChange={handleSelectionChange}
          renderInput={(params) => (
            <TextField {...params} label="Lehrveranstaltung" margin="normal" />
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
                handleDelete={handleDelete}  // add handleDelete prop here
              />
            </Grid>
          ))}
        </Grid>
      )}
    </ContentContainer>
  )
}

export default Dozent;
