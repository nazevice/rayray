import React, { useEffect, useState } from 'react';
import StudyProgramCard from '../ItemCard/StudyProgramCard';
import { TextField, Grid, Typography, Autocomplete, Snackbar, Alert } from '@mui/material';
import FormModal from '../FormModal/FormModal';
import ContentContainer from '../ContentContainer/ContentContainer';

const StudyProgram = () => {
  const [programId, setProgramId] = useState(null);
  const [studyPrograms, setStudyPrograms] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [open, setOpen] = React.useState(false);
  const handleClose = () => setOpen(false);
  const [studyClasses, setStudyClasses] = useState([]);
  const [selectedStudyClasses, setSelectedStudyClasses] = useState([]);
  const [lectures, setLectures] = useState([]);
  const [selectedLectures, setSelectedLectures] = useState([]);
  const [lecturers, setLecturers] = useState([]);
  const [selectedLecturers, setSelectedLecturers] = useState([]);
  const [name, setName] = useState('');
  const [shortName, setShortName] = useState('');
  const [title, setTitle] = useState('');
  const [refreshKey, setRefreshKey] = useState(0);
  const [snackPack, setSnackPack] = React.useState([]);
  const [openSnack, setOpenSnack] = React.useState(false);
  const [messageInfo, setMessageInfo] = React.useState(undefined);
  const [snackbarMessage, setSnackbarMessage] = React.useState('');

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
    fetchData('http://localhost:9090/studyprograms', setStudyPrograms);
    fetchData('http://localhost:9090/studyclasses', setStudyClasses);
    fetchData('http://localhost:9090/lectures', setLectures);
    fetchData('http://localhost:9090/lecturers', setLecturers);
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

  const handleSubmit = (event) => {
    event.preventDefault();

    // Start creating the payload with the form data
    const payload = {
      name: name,
      shortName: shortName,
      id: programId,
    };

    // Determine the appropriate method and URL for the operation
    const method = (title === "Studienprogramm ändern") ? 'PUT' : 'POST';
    const url = 'http://localhost:9090/studyprograms/';

    // Send the form data to our backend server
    fetch(url, {
      method: method,
      body: JSON.stringify(payload),
      headers: {
        'Content-Type': 'application/json',
      },
    }).then((response) => {
      if (response.ok) {
        console.log('Data submitted successfully');
        setRefreshKey(oldKey => oldKey + 1); // trigger a refetch
        setSnackPack((prev) => [...prev, { message: (title === "Studienprogramm ändern") ? 'Daten erfolgreich geändert!' : 'Studienprogramm wurde angelegt!', severity: 'success' }]);
      } else {
        console.error('Error submitting data:', response);
        setSnackPack((prev) => [...prev, { message: 'Fehler beim Verarbeiten der Daten!', severity: 'error' }]);
      }
    });
  };


  const handleDelete = (id) => {
    // Optimistically remove the study program from the local state
    setStudyPrograms(prevPrograms => prevPrograms.filter(program => program.id !== id));

    fetch(`http://localhost:9090/studyprograms/${id}`, {
      method: 'DELETE',
    })
    .then(response => {
      if (response.ok) {
        setSnackPack((prev) => [...prev, { message: 'Studienprogramm wurde entfernt!', severity: 'success' }]);
      } else {
        setSnackPack((prev) => [...prev, { message: 'Fehler beim Entfernen des Studienprogramms!', severity: 'error' }]);
        setRefreshKey(oldKey => oldKey + 1);
      }
    });
}

  const handleOpen = (studyProgram) => {
    if (studyProgram != null) {
      setName(studyProgram.name);
      setShortName(studyProgram.shortName);
      setProgramId(studyProgram.id);
      //   setLectures(studyProgram.lectures);
      //  selectedLecturers(studyProgram.lecturers);
      //  setStudyClasses(studyProgram.studyClasses);
      setTitle("Studienprogramm ändern")
    } else {
      setName('');
      setShortName('');
      setProgramId(null); // No program selected when adding a new one
      //  setLectures(studyProgram.lectures);
      //  selectedLecturers(studyProgram.lecturers);
      //  setStudyClasses(studyProgram.studyClasses);
      setTitle("Studienprogramm hinzufügen")
    }
    setOpen(true)
  }

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

  const handleStudyClassesSelection = (event, value) => {
    setSelectedStudyClasses(value);
  };

  const handleLecturesSelection = (event, value) => {
    setSelectedLectures(value);
  };

  const handleLecturersSelection = (event, value) => {
    setSelectedLecturers(value);
  };

  return (
    <>
      <ContentContainer handleOpen={handleOpen}>
        <FormModal open={open} handleClose={handleClose} handleSubmit={handleSubmit}>
          <Typography>{title}</Typography>
          <TextField
            required
            id="name"
            label="Name"
            name="name"
            margin="normal"
            value={name}
            onChange={(event) => setName(event.target.value)}
            fullWidth
          />
          <TextField
            required
            id="shortName"
            label="Kürzel"
            name="shortName"
            margin="normal"
            value={shortName}
            onChange={(event) => setShortName(event.target.value)}
            fullWidth
          />
          <Autocomplete
            multiple
            options={studyClasses}
            getOptionLabel={(studyClass) => studyClass.name}
            value={selectedStudyClasses}
            onChange={handleStudyClassesSelection}
            renderInput={(params) => (
              <TextField {...params} label="Studienklassen" name="studyClasses" margin="normal" />
            )}
          />
          <Autocomplete
            multiple
            options={lectures}
            getOptionLabel={(lecture) => lecture.lectureName}
            value={selectedLectures}
            onChange={handleLecturesSelection}
            renderInput={(params) => (
              <TextField {...params} label="Lehrveranstaltungen" name="lectures" margin="normal" />
            )}
          />
          <Autocomplete
            multiple
            options={lecturers}
            getOptionLabel={(lecturer) => lecturer.firstName + " " + lecturer.lastName}
            value={selectedLecturers}
            onChange={handleLecturersSelection}
            renderInput={(params) => (
              <TextField {...params} label="Dozent" name="lectureres" margin="normal" />
            )}
          />
        </FormModal>
        {studyPrograms && (
          <Grid container spacing={2}>
            {studyPrograms.map(item => (
              <Grid item xs={4}>
                <StudyProgramCard
                  key={item.id}
                  studyProgram={item}
                  handleOpen={handleOpen}
                  handleDelete={handleDelete} // Pass handleDelete to the Card
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
  )
}
export default StudyProgram;