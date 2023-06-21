import { Box, Card, CardContent, IconButton, Typography } from "@mui/material";
import React from "react";
import DeleteIcon from '@mui/icons-material/Delete';
import EditIcon from '@mui/icons-material/Edit';


const StudyClassCard = ({item}) => {
    return(
        <Card>
            <CardContent>
                <Box>
                    <Typography variant="h5">{item.name}</Typography>
                </Box>
                <Typography>Vorlesungen</Typography>
                <Box>

                </Box>
                <Typography>Dozenten</Typography>
                <Typography>Klassen</Typography>
                <Box>
                
                <IconButton aria-label="add to favorites">
          <DeleteIcon />
        </IconButton>
        <IconButton aria-label="share">
          <EditIcon />
        </IconButton>
                </Box>
            </CardContent>
        </Card>
    );
}
export default  StudyClassCard;