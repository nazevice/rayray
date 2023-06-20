import React from 'react';
import { Box } from '@mui/material'




const Footer = () => {
    return (


        <footer>

            <Box
                sx={{
                    display:'grid',
                    gridTemplateColumns: 'auto 1fr',
                    backgroundColor: 'primary.main',
                    color:'white'
                }}>
                <a href="/impressum" style={{color:'white', margin:'0 15px'}}>Impressum</a>
                <span style={{textAlignLast:'right', margin:'0 15px'}}>© 2023 LectureMaster.</span>
                
            </Box>
        </footer>

    )
}
export default Footer;