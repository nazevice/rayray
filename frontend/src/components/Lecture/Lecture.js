import React, { useEffect, useState, useRef } from "react";
import { TextField, Grid, Typography, Autocomplete } from '@mui/material';
import FormModal from "../FormModal/FormModal";
import LectureCard from "../ItemCard/LectureCard";
import ContentContainer from "../ContentContainer/ContentContainer";

const Lecture = () => {
  const [lectureId, setLectureId] = useState(null);
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
  // Adding this ref to keep track of component's mount status
  const isMounted = useRef(false);

  useEffect(() => {
    isMounted.current = true;
    return () => {
      isMounted.current = false;
    };
  }, []);

  const fetchData = async (url, setData) => {
    const controller = new AbortController();
    try {
      const response = await fetch(url, { signal: controller.signal });
      if (!response.ok) {
        throw new Error(`Failed to fetch data from ${url}. Error: ${response.statusText}`);
      }
      const jsonData = await response.json();
      if (isMounted.current) {
        setData(jsonData);
      }
    } catch (error) {
      if (isMounted.current) {
        setError(`Error fetching data from ${url}: ${error.message}`);
      }
    } finally {
      if (isMounted.current) {
        setLoading(false);
      }
    }

    return () => {
      controller.abort();
    };
  };

  useEffect(() => {
    fetchData('http://localhost:9090/lectures', setLectures);
    fetchData('http://localhost:9090/lecturers', setLecturers);
    fetchData('http://localhost:9090/studyprograms', setStudyPrograms);
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
    let payload = {
      lectureName: value.lectureName,
      modulName: value.modulName,
      duration: value.duration,
      lectureDate: value.lectureDate,
      course: value.course,
    };
    // Send the form data to your backend server
    const url = 'http://localhost:9090/lectures';
    let method = 'POST';
    if (lectureId) {
      method = 'PUT';
      payload = {
        ...payload,
        id: lectureId,
      };
    }
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
        // Reload lectures
        fetchData('http://localhost:9090/lectures', setLectures);
      } else {
        // Handle errors
        console.error('Error submitting data:', response);
      }
    });
    handleClose();
  };


  const handleOpen = (lecture) => {
    if (lecture != null) {
      setLectureId(lecture.id); // Set lectureId when editing
      setLectureName(lecture.lectureName);
      setModuleName(lecture.moduleName);
      setDuration(lecture.duration);
      setTitle("Vorlesung ändern")
    } else {
      setLectureId(null); // Make sure lectureId is null when adding
      setLectureName('');
      setModuleName('');
      setDuration(60);
      setTitle("Vorlesung hinzufügen")
    }
    setOpen(true)
  }
  
  const deleteData = async (url, id) => {
    const response = await fetch(`${url}/${id}`, {
      method: 'DELETE',
    });
  
    if (!response.ok) {
      throw new Error('Failed to delete data');
    }
  
  };
  
  const handleDelete = (id) => {
    deleteData('http://localhost:9090/lectures', id)
      .then(() => {
        // Once the data is deleted, fetch the new data
        fetchData('http://localhost:9090/lectures', setLectures);
      })
      .catch((error) => {
        setError(`Error deleting data: ${error.message}`);
      });
  };

  const handleLecturersSelection = (event, value) => {
    setSelectedLecturers(value);
  };

  const handleStudyProgramSelection = (event, value) => {
    setSelectedStudyProgram(value);
  };

  return (
    <ContentContainer handleOpen={() => handleOpen(null)}>
      <FormModal open={open} handleClose={handleClose} handleSubmit={handleSubmit}>
        <Typography>{title}</Typography>
        <TextField
          fullWidth
          label="Name der Lehrveranstaltung"
          name="lectureName"
          margin="normal"
          value={lectureName}
          onChange={(event) => setLectureName(event.target.value)}
        />
        <TextField
          fullWidth
          label="Name des Moduls"
          name="modulName"
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
          onChange={(event) => setDuration(event.target.value)}
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
                handleDelete={handleDelete}
              />
            </Grid>
          ))}
        </Grid>
      )}
    </ContentContainer>
  );
};

export default Lecture;
