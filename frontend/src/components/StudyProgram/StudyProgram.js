import React, { useEffect, useState } from 'react';
import StudyProgramCard from '../ItemCard/StudyProgramCard';
import { Box, Grid, TextField, Button } from '@mui/material';
import FormModal from '../FormModal/FormModal';

const StudyProgram = () => {
    const [data, setData] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [open, setOpen] = React.useState(false);
    const handleOpen = () => setOpen(true);
    const handleClose = () => setOpen(false);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await fetch('http://localhost:9090/studyprograms');
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
    return (
        <Box fill padding={2}>
            <FormModal open={open} handleClose={handleClose} handleSubmit={handleSubmit}>
                <TextField required id="name" label="Name" name="name" margin="normal" fullWidth />
                <TextField required id="shortName" label="Kürzel" name="shortName" margin="normal" fullWidth />
                <TextField id="course" label="Studiengang" name="course" margin="normal" fullWidth />
                <TextField id="lecture" label="Lehrveranstaltung" name="lecture" margin="normal" fullWidth />
                <TextField id="lecturer" label="Dozent" name="lecturer" margin="normal" fullWidth />
            </FormModal>
            {data && (
                <Grid container spacing={2}>
                    {data.map(item => (
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
