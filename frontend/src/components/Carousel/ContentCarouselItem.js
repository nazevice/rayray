import React from 'react';
import { useTheme } from '@emotion/react';

const ContentCarouselItem = ({ title, children }) => {
  const theme = useTheme();
  return (
    <div style={{ backgroundColor: theme.palette.secondary.main, padding: '24px', borderRadius: '8px' }}>
      {children}
    </div>
  );
};

export default ContentCarouselItem;
