import { Box, IconButton, Typography } from "@mui/material"
import AddCircleIcon from '@mui/icons-material/AddCircle';

const ContentContainer = ({ children, handleOpen }) => {
    let path = window.location.pathname.replace(/^\/+|\/+$/g, '');
    const capitalizeFirstChar = (str) => {
        return str.charAt(0).toUpperCase() + str.slice(1);
    }
    path = capitalizeFirstChar(path);
    return (
        <Box padding={3}>
            <Box style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between' }}>
                <Typography>{path}</Typography>
                <IconButton aria-label="share" onClick={() => handleOpen(null)}>
                    <AddCircleIcon />
                </IconButton>
            </Box>
            <Box>
                {children}
            </Box>
        </Box>
    );
}
export default ContentContainer;