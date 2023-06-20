import React from 'react';
import { Box, Input, Text } from 'dracula-ui';

const StudyProgram = () => {
    return (
        <Box color="" width='md'>
            <Text color="black">Name:</Text>
            <Input placeholder="Input"/>
            <Text color="black">Kürzel:</Text>
            <Input placeholder="Input" />
            <Text color="black">Studiengang:</Text>
            <Input placeholder="Input" />
            <Text color="black">Lehrveranstaltung:</Text>
            <Input placeholder="Input" />
            <Text color="black">Dozent:</Text>
            <Input placeholder="Input" />       
        </Box>
    )
}
export default StudyProgram;