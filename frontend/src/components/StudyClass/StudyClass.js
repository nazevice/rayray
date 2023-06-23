import { Autocomplete, Box, Grid, TextField } from "@mui/material";
import React, { useEffect, useState } from "react";
import FormModal from "../FormModal/FormModal";
import StudyClassCard from "../ItemCard/StudyClassCard";
import { DatePicker } from "@mui/x-date-pickers";
import ContentContainer from "../ContentContainer/ContentContainer";

const StudyClass = () => {
    const [studyClasses, setStudyClasses] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [open, setOpen] = React.useState(false);
    const handleOpen = () => setOpen(true);
    const handleClose = () => setOpen(false);
    const [studyPrograms, setStudyPrograms] = useState(null);
    const [selectedStudyProgram, setSelectedStudyProgram] = useState([]);

    useEffect(() => {
        fetchData('http://localhost:9090/studyclasses', setStudyClasses);
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
            className: value.className,
            startDate: value.startDate,
            endDate: value.endDate,
        };
        // Send the form data to your backend server
        fetch('http://localhost:9090/studyclasses', {
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

    const handleStudyProgramSelection = (event, value) => {
        setSelectedStudyProgram(value);
    };

    return (
        <ContentContainer handleOpen={handleOpen}>
            <FormModal open={open} handleClose={handleClose} handleSubmit={handleSubmit}>
                <TextField
                    fullWidth
                    label="Name der Studienklasse"
                    name="className"
                    margin="normal"
                />
                <DatePicker
                    fullWidth
                    label="Beginn der Studienklasse"
                    name="startDate"
                    margin="normal"
                />
                <DatePicker
                    fullWidth
                    label="Ende der Studienklasse"
                    name="endDate"
                    margin="normal"
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
            {studyClasses && (
                <Grid container spacing={2}>
                    {studyClasses.map(item => (
                        <Grid item xs={4}>
                            <StudyClassCard
                                key={item.id}
                                studyClass={item}
                                handleOpen={handleOpen}
                            />
                        </Grid>
                    ))}
                </Grid>
            )}
        </ContentContainer>
    )
}
export default StudyClass;
