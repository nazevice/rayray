import { Box, Card, CardContent, IconButton, Typography } from "@mui/material";
import React from "react";
import DeleteIcon from '@mui/icons-material/Delete';
import EditIcon from '@mui/icons-material/Edit';

const LectureCard = ({ lecture, handleOpen, handleDelete }) => {
  return (
    <Card>
      <CardContent>
        <Box>
          <Typography variant="h5">{lecture.lectureName}</Typography>
          <Typography sx={{ fontSize: 14 }} color="text.secondary" gutterBottom>{lecture.moduleName}</Typography>
        </Box>
        <Typography>Vorlesungen</Typography>
        <Typography>Dozenten</Typography>
        <Typography>Klassen</Typography>
        <Box sx={{ display: 'flex', justifyContent: 'flex-end', marginTop: 'auto' }}>
          <IconButton aria-label="delete" onClick={() => handleDelete(lecture.id)}>
            <DeleteIcon />
          </IconButton>
          <IconButton aria-label="edit" onClick={() => handleOpen(lecture)}>
            <EditIcon />
          </IconButton>
        </Box>
      </CardContent>
    </Card>
  );
}

export default LectureCard;
