import React, { useEffect, useState } from 'react';
import StudyProgramCard from '../ItemCard/StudyProgramCard';
import { Box, Button, Grid, Modal, TextField } from '@mui/material';
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

    if (loading) {
        return <div>Loading...</div>;
    }

    if (error) {
        return <div>Error: {error}</div>;
    }
    return (
        <Box fill padding={2}>
            <Modal open={open} onClose={handleClose} sx={{ display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
                <Box height="auto" width="50%" bgcolor="white" padding={2} m={2}>
                    <TextField
                        fullWidth
                        required
                        id="name"
                        label="Name"
                        margin="normal"
                    />
                    <TextField
                        fullWidth
                        required
                        id="shortName"
                        label="Kürzel"
                        margin="normal"
                    />
                    <TextField
                        fullWidth
                        id="outlined-required"
                        label="Studiengang"
                        margin="normal"
                    />
                    <TextField
                        fullWidth
                        id="outlined-required"
                        label="Lehrveranstaltung"
                        margin="normal"
                    />
                    <TextField
                        fullWidth
                        id="outlined-required"
                        label="Dozent"
                        margin="normal"
                    />
                    <Button>Ok</Button>
                </Box>
            </Modal>
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