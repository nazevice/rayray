import React from 'react';
import { Box, Card, Text, Button } from 'dracula-ui';


const ContentCarouselItem = ({title, children}) => {
    return (
        <Card width="full" display="inline-block" rounded="lg">
            <Box>
                <Text fontSize="18px" fontWeight="bold" mt="16px">{title}</Text>
            </Box>
            <Text mt="8px">{children}</Text>
        </Card>
    )
}
export default ContentCarouselItem;