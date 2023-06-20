import React from 'react';
import { Box } from 'dracula-ui';
import ContentCarousel from '../Carousel/ContentCarousel';

const Content = () => {
    return(
        <Box display="flex" height='2xl' color='black'>
             <ContentCarousel />
             <ContentCarousel />
        </Box>
    )
}
export default Content;