import React from 'react';
import { Box, Card, Typography } from '@mui/material';

const ContentCarouselItem = ({ title, children }) => {
  return (
    <div style={{ backgroundColor: 'primary', padding: '16px', borderRadius: '8px' }}>
      <Card width="full" display="inline-block" rounded="lg">
        <Box>
       
            <Typography fontSize="18px" fontWeight="bold" mt="16px">{title}</Typography>
          
        </Box>
        <div style={{ backgroundColor: 'white', padding: '8px', borderRadius: '4px' }}>
          <Typography mt="8px">{children}</Typography>
        </div>
      </Card>
    </div>
  );
};

export default ContentCarouselItem;
