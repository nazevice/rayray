import { Box, Card, CardContent, IconButton, Typography } from "@mui/material";
import React from "react";
import DeleteIcon from '@mui/icons-material/Delete';
import EditIcon from '@mui/icons-material/Edit';


const StudyProgramCard = ({ studyProgram, handleOpen, handleDelete }) => {
  return (
    <Card>
      <CardContent>
        <Box>
          <Typography variant="h5">{studyProgram.shortName}</Typography>
          <Typography sx={{ fontSize: 14 }} color="text.secondary" gutterBottom>{studyProgram.name}</Typography>
        </Box>
        <Typography>Vorlesungen</Typography>
        <Box>
        </Box>
        <Typography>Dozenten</Typography>
        <Typography>Klassen</Typography>
        <Box sx={{ display: 'flex', justifyContent: 'flex-end', marginTop: 'auto' }}>
        <IconButton aria-label="delete" onClick={() => handleDelete(studyProgram.id)}>
            <DeleteIcon />
        </IconButton>
          <IconButton aria-label="edit" onClick={() => handleOpen(studyProgram)}>
            <EditIcon />
          </IconButton>
        </Box>
      </CardContent>
    </Card>
  );
}
export default StudyProgramCard;