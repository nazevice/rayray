import Carousel from 'react-bootstrap/Carousel';
import { Box, Card, Text, Button } from 'dracula-ui';
import ContentCarousel from '../Carousel/ContentCarousel';

const Content = () => {
    return(
        <Box display="flex" height='2xl' color='black'>
             <ContentCarousel />
        </Box>
    )
}
export default Content;