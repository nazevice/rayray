import React from 'react';
import { Box, Input, Text } from 'dracula-ui';

const StudyProgram = () => {
    return (
        <Box color="black" width='md'>
            <Text color="black">Name:</Text>
            <Input color="white" type="date" size="sm" placeholder="small" m="xs" />
            
        </Box>
    )
}
export default StudyProgram;