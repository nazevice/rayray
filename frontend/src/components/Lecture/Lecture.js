import React, { useEffect, useState, useRef } from "react";
import { TextField, Grid, Typography, Autocomplete, Snackbar, Alert } from '@mui/material';
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
  const [snackPack, setSnackPack] = React.useState([]);
  const [openSnack, setOpenSnack] = React.useState(false);
  const [messageInfo, setMessageInfo] = React.useState(undefined);
  const [snackbarMessage, setSnackbarMessage] = React.useState('');

  const handleClose = () => setOpen(false);
  // Adding this ref to keep track of component's mount status
  const isMounted = useRef(false);

  React.useEffect(() => {
    if (snackPack.length && !snackbarMessage) {
      setSnackbarMessage(snackPack[0].message);
      setMessageInfo({ ...snackPack[0] });
      setOpenSnack(true);
      setSnackPack((prev) => prev.slice(1));
    } else if (snackPack.length > 0 && messageInfo && openSnack === false) {
      setSnackbarMessage('');
      setMessageInfo(undefined);
    }
  }, [snackPack, messageInfo, openSnack, snackbarMessage]);

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
        setSnackPack((prev) => [...prev, { message: (title === "Vorlesung ändern") ? 'Daten erfolgreich geändert!' : 'Lehrveranstaltung wurde angelegt!', severity: 'success' }]);
      } else {
        // Handle errors
        console.error('Error submitting data:', response);
        setSnackPack((prev) => [...prev, { message: 'Speichern fehlgeschlagen!', severity: 'error' }]);
      }
    });
    handleClose();
  };


  const handleOpen = (lecture) => {
    if (lecture != null) {
      setLectureId(lecture.id); // Set lectureId when editing
      setLectureName(lecture.lectureName);
      setModuleName(lecture.modulName);
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

    if (response.ok) {
      setSnackPack((prev) => [...prev, { message: 'Dozent wurde entfernt!', severity: 'success' }]);
    } else {
      setSnackPack((prev) => [...prev, { message: 'Fehler beim Entfernen des Dozenten!', severity: 'error' }]);
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

  const processQueue = () => {
    if (snackPack.length > 0) {
      setMessageInfo({ ...snackPack[0] });
      setSnackPack((prev) => prev.slice(1));
      setOpenSnack(true);
    }
  };

  const handleSnackClose = (event, reason) => {
    if (reason === 'clickaway') {
      return;
    }
    setOpenSnack(false);
  };

  const handleLecturersSelection = (event, value) => {
    setSelectedLecturers(value);
  };

  const handleStudyProgramSelection = (event, value) => {
    setSelectedStudyProgram(value);
  };

  return (
    <>
      <ContentContainer handleOpen={() => handleOpen(null)}>
        <FormModal open={open} handleClose={handleClose} handleSubmit={handleSubmit}>
          <Typography>{title}</Typography>
          <TextField
            required
            fullWidth
            label="Name der Lehrveranstaltung"
            name="lectureName"
            margin="normal"
            value={lectureName}
            onChange={(event) => setLectureName(event.target.value)}
          />
          <TextField
            required
            fullWidth
            label="Name des Moduls"
            name="modulName"
            margin="normal"
            value={moduleName}
            onChange={(event) => setModuleName(event.target.value)}
          />
          <TextField
            required
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
          <Grid container spacing={2} padding={0}>
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
      <Snackbar
        key={messageInfo ? messageInfo.key : undefined}
        anchorOrigin={{
          vertical: 'bottom',
          horizontal: 'left',
        }}
        open={openSnack}
        autoHideDuration={4000}
        onClose={handleSnackClose}
        onExited={() => {
          processQueue();
          setSnackbarMessage('');
        }}
        style={{ bottom: 50 }}
      >
        <Alert
          onClose={handleSnackClose}
          severity={messageInfo ? messageInfo.severity : "success"}
          sx={{ width: '100%' }}
        >
          {snackbarMessage}
        </Alert>
      </Snackbar>
    </>
  );
};

export default Lecture;
