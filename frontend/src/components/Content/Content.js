import React from 'react';
import ContentCarousel from '../Carousel/ContentCarousel';
import { Box, Button, Grid, Typography, useTheme } from '@mui/material';
import { Player } from '@lottiefiles/react-lottie-player';

const Content = () => {
    const theme = useTheme();
    return (
        <Grid container>
            <Grid item xs={6}>
                <Box marginLeft={3} marginTop={6}>
                    <Box width="100%">
                        <Typography 
                            variant="h2" 
                            fontFamily="Poppins" 
                            fontWeight="600"
                            sx={{color: theme.palette.custom.fontMain}}
                        >
                            Simples Dashboard
                        </Typography>
                        <Typography sx={{color: theme.palette.custom.fontMain, marginBottom: "16px"}}>
                            Als Dozent können sie über das Dashboard sich die Details über die angebotenen
                            Vorlesungen einsehen und ihre geplanten Vorlesungen anschauen.
                        </Typography>
                    </Box>
                    <Button href="http://localhost:3000/dashboard" sx={{color: theme.palette.custom.fontSecondary}} variant="contained">Jetzt beginnen</Button>
                </Box>
            </Grid>
            <Grid item xs={6}>
                <Box width="100%">
                    <Player
                        src="https://assets8.lottiefiles.com/packages/lf20_w4kou3mriQ.json"
                        className="player"
                        loop
                        autoplay
                    />
                </Box>
            </Grid>
            <Grid item xs={12}>
            <Box width="100%">
                <ContentCarousel />
            </Box>
            </Grid>
        </Grid>
    );
};
export default Content;