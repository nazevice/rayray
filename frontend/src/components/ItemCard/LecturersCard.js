import { Box, Card, CardContent, IconButton, Typography } from "@mui/material";
import React from "react";
import DeleteIcon from '@mui/icons-material/Delete';
import EditIcon from '@mui/icons-material/Edit';

const LecturersCard = ({ lecturer, handleOpen, handleDelete, isDashboard }) => {
  let style = { minHeight: "150px", display: 'flex', flexDirection: 'column'};
  let style2 = { minHeight: "100px", minWidth: "24%", maxWidth: "24%", display: 'flex', flexDirection: 'column'}
  return (
    <Card sx={isDashboard ? style2:style}>
  <CardContent sx={{flexGrow: 1}}>
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
        {lecturer.firstName + " " + lecturer.lastName}
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
        {lecturer.email}
      </Typography>
    </Box>
  </CardContent>
  {isDashboard ? null : (
  <Box sx={{ display: 'flex', justifyContent: 'flex-end', marginBottom: '1rem' }}>
    <IconButton style={{ color: '#B31312' }} aria-label="delete" onClick={() => handleDelete(lecturer.id)}>
      <DeleteIcon />
    </IconButton>
    <IconButton aria-label="share" onClick={() => handleOpen(lecturer)}>
      <EditIcon />
    </IconButton>
  </Box>
  )}
</Card>
  );
};
export default LecturersCard;
