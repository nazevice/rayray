import React, { useEffect, useState } from 'react';
import StudyProgramCard from '../ItemCard/StudyProgramCard';
import { Autocomplete, Box, Grid, TextField } from '@mui/material';
import FormModal from '../FormModal/FormModal';

const StudyProgram = () => {
    const [studyPrograms, setStudyPrograms] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [open, setOpen] = React.useState(false);
    const handleOpen = () => setOpen(true);
    const handleClose = () => setOpen(false);
    const [studyClasses, setStudyClasses] = useState([]);
    const [selectedStudyClasses, setSelectedStudyClasses] = useState([]);
    const [lectures, setLectures] = useState([]);
    const [selectedLectures, setSelectedLectures] = useState([]);
    const [lecturers, setLecturers] = useState([]);
    const [selectedLecturers, setSelectedLecturers] = useState([]);
    

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

    const handleSubmit = (event) => {
        event.preventDefault();
        // Implement form submission logic here
    };

    if (loading) {
        return <div>Loading...</div>;
    }

    if (error) {
        return <div>Error: {error}</div>;
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
        <Box fill padding={2}>
            <FormModal open={open} handleClose={handleClose} handleSubmit={handleSubmit}>
                <TextField
                    id="name"
                    label="Name"
                    name="name"
                    margin="normal"
                    fullWidth
                />
                <TextField
                    id="shortName"
                    label="Kürzel"
                    name="shortName"
                    margin="normal"
                    fullWidth
                />
                <Autocomplete
                    multiple
                    options={studyClasses}
                    getOptionLabel={(studyClass) => studyClass.name}
                    value={selectedStudyClasses}
                    onChange={handleStudyClassesSelection}
                    renderInput={(params) => (
                        <TextField {...params} label="Studienklasse" margin="normal" />
                    )}
                />
                <Autocomplete
                    multiple
                    options={lectures}
                    getOptionLabel={(lecture) => lecture.lectureName}
                    value={selectedLectures}
                    onChange={handleLecturesSelection}
                    renderInput={(params) => (
                        <TextField {...params} label="Lehrveranstaltung" margin="normal" />
                    )}
                />
                <Autocomplete
                    multiple
                    options={lecturers}
                    getOptionLabel={(lecturer) => lecturer.firstName + " " + lecturer.lastName}
                    value={selectedLecturers}
                    onChange={handleLecturersSelection}
                    renderInput={(params) => (
                        <TextField {...params} label="Dozent" margin="normal" />
                    )}
                />
            </FormModal>
            {studyPrograms && (
                <Grid container spacing={2}>
                    {studyPrograms.map(item => (
                        <Grid item xs={4}>
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
export default StudyProgram;
