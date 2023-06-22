import React, { useEffect, useState } from "react";
import { TextField, Grid, Typography, Autocomplete } from '@mui/material';
import FormModal from "../FormModal/FormModal";
import LectureCard from "../ItemCard/LectureCard";
import ContentContainer from "../ContentContainer/ContentContainer";

const Lecture = () => {
  const [lectures, setLectures] = useState(null);
  const [lecturers, setLecturers] = useState(null);
  const [studyPrograms, setStudyPrograms] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [title, setTitle] = useState('');
  const [open, setOpen] = useState(false);
  const [lectureName, setLectureName] = useState('');
  const [moduleName, setModuleName] = useState('');
  const [duration, setDuration] = useState(60);
  const [selectedLecturers, setSelectedLecturers] = useState([]);
  const [selectedStudyProgram, setSelectedStudyProgram] = useState([]);
  const handleClose = () => setOpen(false);

  useEffect(() => {
    fetchData('http://localhost:9090/lectures', setLectures);
    fetchData('http://localhost:9090/lecturers', setLecturers);
    fetchData('http://localhost:9090/studyprograms', setStudyPrograms);
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

  const handleOpen = (lecture) => {
    if (lecture != null) {
      setLectureName(lecture.lectureName);
      setModuleName(lecture.modulName);
      setDuration(lecture.duration);
      setTitle("Vorlesung ändern")
    } else {
      setLectureName('');
      setModuleName('');
      setDuration(60);
      setTitle("Vorlesung hinzufügen")
    }
    setOpen(true)
  }

  const handleLecturersSelection = (event, value) => {
    setSelectedLecturers(value);
  };

  const handleStudyProgramSelection = (event, value) => {
    setSelectedStudyProgram(value);
  };

  return (
    <ContentContainer handleOpen={handleOpen}>
      <FormModal open={open} handleClose={handleClose} handleSubmit={handleSubmit}>
        <Typography>{title}</Typography>
        <TextField
          fullWidth
          label="Name der Lehrveranstaltung"
          name="lectureName"
          margin="normal"
          value={lectureName}
          onChange={(event) => setLectureName(event.value.target)}
        />
        <TextField
          fullWidth
          label="Name des Moduls"
          name="moduleName"
          margin="normal"
          value={moduleName}
          onChange={(event) => setModuleName(event.target.value)}
        />
        <TextField
          fullWidth
          label="Vorlesungszeit in Minuten"
          name="duration"
          margin="normal"
          type="number"
          value={duration}
          onChange={(event) => setDuration(event)}
        />
        <Autocomplete
          multiple
          options={lecturers}
          getOptionLabel={(lecturers) => lecturers.firstName + " " + lecturers.lastName}
          value={selectedLecturers}
          onChange={handleLecturersSelection}
          renderInput={(params) => (
            <TextField {...params} label="Dozent" margin="normal" />
          )}
        />
        <Autocomplete
          options={studyPrograms}
          getOptionLabel={(studyPrograms) => studyPrograms.name}
          value={selectedStudyProgram}
          onChange={handleStudyProgramSelection}
          renderInput={(params) => (
            <TextField {...params} label="Studiengang" margin="normal" />
          )}
        />
      </FormModal>
      {lectures && (
        <Grid container spacing={2}>
          {lectures.map(item => (
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
    </ContentContainer>
  );
};

export default Lecture;
