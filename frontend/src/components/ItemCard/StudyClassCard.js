import { Box, Card, CardContent, IconButton, Typography, useTheme } from "@mui/material";
import React from "react";
import DeleteIcon from '@mui/icons-material/Delete';
import EditIcon from '@mui/icons-material/Edit';


const StudyClassCard = ({studyClass, handleDelete, handleOpen}) => {
  const theme = useTheme();
  return(
    <Card 
      sx={{ 
        backgroundColor: theme.palette.secondary.main, 
        maxWidth: "350px", 
        minHeight: "150px", 
        display: 'flex', 
        flexDirection: 'column',
        color: theme.palette.custom.fontMain
      }}>
    <CardContent sx={{ flexGrow: 1 }}>
      <Box>
        <Typography 
          sx={{ 
            textOverflow: 'ellipsis', 
            overflow: 'hidden', 
            whiteSpace: 'nowrap', 
            maxWidth: '350px',
            color: "theme.custom.fontMain"
          }} 
          variant="h5"
        >
          {studyClass.name}
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
          {studyClass.startDate == null ? "Nicht verfügbar" 
          : new Date(...studyClass.startDate).toISOString().split("T")[0] + " bis " + new Date(...studyClass.endDate).toISOString().split("T")[0]}
        </Typography>
      </Box>
    </CardContent>
    <Box sx={{ display: 'flex', justifyContent: 'flex-end', marginBottom: '1rem' }}>
      <IconButton style={{ color: '#B31312' }} aria-label="delete" onClick={() => handleDelete(studyClass.id)}>
        <DeleteIcon />
      </IconButton>
      <IconButton style={{ color: theme.palette.primary.main }} aria-label="share" onClick={() => handleOpen(studyClass)}>
        <EditIcon />
      </IconButton>
    </Box>
  </Card>
    );
}
export default  StudyClassCard;