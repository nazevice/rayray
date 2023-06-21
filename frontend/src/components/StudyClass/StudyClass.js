import { Box, Grid, TextField } from "@mui/material";
import React, { useEffect, useState } from "react";
import FormModal from "../FormModal/FormModal";
import LecturersCard from "../ItemCard/LecturersCard";
import StudyClassCard from "../ItemCard/StudyClassCard";

const StudyClass = () => {
    const [data, setData] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [open, setOpen] = React.useState(false);
    const handleOpen = () => setOpen(true);
    const handleClose = () => setOpen(false);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await fetch('http://localhost:9090/studyclasses');
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
                <TextField label="Name Studienklasse" name="firstName" fullWidth />
                <TextField label="Beginn Studenklasse" name="lastName" fullWidth />
                <TextField label="Ende Studenklasse" name="email" type="email" fullWidth />
            </FormModal>
            {data && (
        <Grid container spacing={2}>
          {data.map(item => (
            <Grid item xs={4}>
            <StudyClassCard 
                key={item.id}
                item={item}
            />
            </Grid>
          ))}
        </Grid>
      )}
        </Box>
    )
}
export default StudyClass;