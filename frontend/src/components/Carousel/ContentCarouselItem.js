import React from 'react';
import { Box, Card, Text, Button } from 'dracula-ui';


const ContentCarouselItem = ({path, title, text, btnText}) => {
    return (
        <Card width="xs" display="inline-block" rounded="lg">
            <img src={window.location.origin +"/banner.webp"} height="200px" />
            <Box>
                <Text fontSize="18px" fontWeight="bold" mt="16px">Class B</Text>
            </Box>
            <Text mt="8px">Lorem ipsum dolor sit amet, consectetur adipiscing elit.</Text>
            <Button mt="16px" variant="primary">Read More</Button>
        </Card>
    )
}
export default ContentCarouselItem;