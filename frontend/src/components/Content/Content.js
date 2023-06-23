import React from 'react';
import ContentCarousel from '../Carousel/ContentCarousel';
import { Box, Button, Grid, Typography } from '@mui/material';
import { Player } from '@lottiefiles/react-lottie-player';

const Content = () => {
    return (
        <Grid container spacing={2}>
            <Grid item xs={6}>
                <Box marginTop="2em" padding="5em">
                    <Box width="100%" style={{marginLeft:'-50px'}}>
                        <Typography variant="h2" fontFamily="Poppins" fontWeight="600">Simples Dashboard</Typography>
                        <Typography>
                            Als Dozent können sie sich die Details über die angebotenen
                            Vorlesungen einsehen und geplanten Vorlesungen einsehen.
                        </Typography>
                    </Box>
                    <Button variant="contained" style={{marginLeft:'-50px'}}>Jetzt beginnen</Button>
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