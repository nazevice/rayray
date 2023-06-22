import { Box, Grid, TextField } from "@mui/material";
import React, { useEffect, useState } from "react";
import FormModal from "../FormModal/FormModal";
import StudyProgramCard from "../ItemCard/StudyProgramCard";

const Semester = () => {
    const [data, setData] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [open, setOpen] = React.useState(false);
    const handleOpen = () => setOpen(true);
    const handleClose = () => setOpen(false);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await fetch('http://localhost:9090/semesters');
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
            <FormModal open={open} handleClose={handleClose}>
                <TextField
                    fullWidth
                    required
                    id="name"
                    label="Beginn des Semesters:"
                    margin="normal"
                />
                <TextField
                    fullWidth
                    required
                    id="name"
                    label="Ende des Semesters:"
                    margin="normal"
                />
                <TextField
                    fullWidth
                    required
                    id="name"
                    label="Nummer des Semesters:"
                    margin="normal"
                />
                <TextField
                    fullWidth
                    required
                    id="name"
                    label="Name der Semesters:"
                    margin="normal"
                />
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
export default Semester;