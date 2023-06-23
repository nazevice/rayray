import React from 'react';
import { Box, Typography } from '@mui/material'



const Footer = () => {
    return (


        <footer style={{ marginTop: 'auto' }}>

            <Box
                sx={{
                    display: 'grid',
                    gridTemplateColumns: 'auto auto 1fr',
                    backgroundColor: 'primary.main',
                    color: 'white'
                }}>
                <Typography variant="body1" component="a" href="/impressum" style={{color: 'white', margin: '0 10px 0 24px' }}>
                    Impressum
                </Typography>
                <Typography variant="body1" component="a" href="/datenschutz" style={{ color: 'white', margin: '0 10px' }}>
                    Datenschutz
                </Typography>

                <span style={{ textAlignLast: 'right', margin: '0 24px' }}>© 2023 LectureMaster</span>

            </Box>
        </footer>

    )
}
export default Footer;