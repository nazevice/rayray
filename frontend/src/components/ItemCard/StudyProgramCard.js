import { Box, Card, CardContent, IconButton, Typography, useTheme } from "@mui/material";
import React from "react";
import DeleteIcon from '@mui/icons-material/Delete';
import EditIcon from '@mui/icons-material/Edit';


const StudyProgramCard = ({ studyProgram, handleOpen, handleDelete }) => {
  const theme = useTheme();
  return (
    <Card 
      sx={{ 
        maxWidth: "350px", 
        minHeight: "150px", 
        display: 'flex', 
        flexDirection: 'column' 
      }}
    >
    <CardContent sx={{ flexGrow: 1 }}>
      <Box>
        <Typography 
          sx={{ 
            textOverflow: 'ellipsis', 
            overflow: 'hidden', 
            whiteSpace: 'nowrap', 
            maxWidth: '350px'
          }} 
          variant="h5"
        >
          {studyProgram.name}
        </Typography>
        <Typography 
          sx={{
            textOverflow: 'ellipsis', 
            overflow: 'hidden', 
            whiteSpace: 'nowrap', 
            maxWidth: '350px', 
            fontSize: "14"
          }} 
          gutterBottom
        >
          {studyProgram.shortName}
        </Typography>
      </Box>
    </CardContent>
    <Box 
      sx={{
         display: 'flex', 
         justifyContent: 'flex-end', 
         marginBottom: '1rem' 
         }}
    >
      <IconButton style={{ color: '#B31312' }} aria-label="delete" onClick={() => handleDelete(studyProgram.id)}>
        <DeleteIcon />
      </IconButton>
      <IconButton style={{ color: theme.palette.primary.main }} aria-label="share" onClick={() => handleOpen(studyProgram)}>
        <EditIcon />
      </IconButton>
    </Box>
  </Card>
  );
}
export default StudyProgramCard;