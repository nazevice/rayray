import React, { useEffect, useState } from 'react';
import StudyProgramCard from '../ItemCard/StudyProgramCard';
import { Autocomplete, Grid, TextField, Typography } from '@mui/material';
import FormModal from '../FormModal/FormModal';
import ContentContainer from '../ContentContainer/ContentContainer';

const StudyProgram = () => {
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
    

    useEffect(() => {
        fetchData('http://localhost:9090/studyprograms', setStudyPrograms);
        fetchData('http://localhost:9090/studyclasses', setStudyClasses);
        fetchData('http://localhost:9090/lectures', setLectures);
        fetchData('http://localhost:9090/lecturers', setLecturers);
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
      
        // Start creating the payload with the form data
        const payload = {
          name: value.name,
          shortName: value.shortName,
          studyClasses: value.studyClasses,
          lectures: value.lectures,
          lecturers: value.lecturers,
        };
      
        // Determine the appropriate method and URL for the operation
        const method = (title === "Studienprogramm ändern") ? 'PUT' : 'POST';
        const url = 'http://localhost:9090/studyprograms';
      
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
          } else {
            // Handle errors
            console.error('Error submitting data:', response);
          }
        });
      };

    const handleOpen = (studyProgram) => {
        if(studyProgram!=null) {
          setName(studyProgram.name);
          setShortName(studyProgram.shortName);
          setSelectedStudyClasses(StudyProgram.studyClasses);
          setSelectedLectures(studyProgram.lectures);
          setSelectedLecturers(studyProgram.lecturers);
          setTitle("Studienprogramm ändern")
        } else {
            setName('');
            setShortName('');
            setSelectedStudyClasses([]);
            setSelectedLectures([]);
            setSelectedLecturers([]);
          setTitle("Studienprogramm hinzufügen")
        }
        setOpen(true)
      }

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
        <ContentContainer handleOpen={handleOpen}>
            <FormModal open={open} handleClose={handleClose} handleSubmit={handleSubmit}>
                <Typography>{title}</Typography>
                <TextField
                    id="name"
                    label="Name"
                    name="name"
                    margin="normal"
                    value={name}
                    onChange={(event) => setName(event.target.value)}
                    fullWidth
                />
                <TextField
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
                            />
                        </Grid>
                    ))}
                </Grid>
            )}
        </ContentContainer>
    )
}
export default StudyProgram;
