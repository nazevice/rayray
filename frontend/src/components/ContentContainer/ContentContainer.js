import { Box, IconButton, Typography } from "@mui/material"
import AddCircleIcon from '@mui/icons-material/AddCircle';
import { useTheme } from "@emotion/react";

const ContentContainer = ({ children, handleOpen }) => {
    let path = window.location.pathname.replace(/^\/+|\/+$/g, '');
    const capitalizeFirstChar = (str) => {
        return str.charAt(0).toUpperCase() + str.slice(1);
    }
    path = capitalizeFirstChar(path);
    if(path==="Dozent") {
        path="Dozenten";
    } else if(path==="Vorlesung") {
        path="Vorlesungen";
    } else if(path==="Studienklasse") {
        path="Studienklassen";
    } else if(path==="Studienprogramm") {
        path="Studienprogramme"
    }
    const theme = useTheme();
    return (
        <Box padding={3}>
            <Box style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between' }}>
                <Typography variant="h4" fontFamily="Poppins" marginBottom={1}>{path}</Typography>
                {path === "Dashboard" ? null : (<IconButton style={{ color: theme.palette.primary.main }} aria-label="share" onClick={() => handleOpen(null)}>
                    <AddCircleIcon />
                </IconButton>
                )}
            </Box>
            <Box>
                {children}
            </Box>
        </Box>
    );
}
export default ContentContainer;