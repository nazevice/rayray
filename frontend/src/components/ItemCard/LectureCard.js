import { Box, Card, CardContent, IconButton, Typography, useTheme } from "@mui/material";
import React from "react";
import DeleteIcon from '@mui/icons-material/Delete';
import EditIcon from '@mui/icons-material/Edit';

const LectureCard = ({ lecture, handleOpen, handleDelete }) => {
  const theme = useTheme();
  return (
    <Card 
      sx={{ 
        minHeight: "180px", 
        display: 'flex', 
        flexDirection: 'column',
        backgroundColor: theme.palette.secondary.main, 
        color: theme.palette.custom.fontMain 
        }}
      >
      <CardContent sx={{ flexGrow: 1 }}>
        <Box>
          <Typography 
            sx={{
              textOverflow: 'ellipsis', 
              overflow: 'hidden', 
              whiteSpace: 'nowrap',
              maxWidth: '350px',
            }} 
            variant="h5"
          >
            {lecture.lectureName}
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
            {lecture.modulName}
          </Typography>
          <Typography 
            sx={{
              textOverflow: 'ellipsis',
              overflow: 'hidden', 
              whiteSpace: 'nowrap',
              maxWidth: '350px', 
              fontSize: "14"
            }} 
            color="text.secondary" 
            gutterBottom
          >
            {lecture.duration + " Minuten"}
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
        <IconButton style={{ color: '#B31312' }} aria-label="delete" onClick={() => handleDelete(lecture.id)}>
          <DeleteIcon />
        </IconButton>
        <IconButton style={{ color: theme.palette.primary.main }} aria-label="share" onClick={() => handleOpen(lecture)}>
          <EditIcon />
        </IconButton>
      </Box>
    </Card>
  );
}

export default LectureCard;
