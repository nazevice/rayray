import React from 'react';
import { Box, Input, Text } from 'dracula-ui';

const StudyProgram = () => {
    return (
        <Box color="" width='md'>
            <Text color="black">Name:</Text>
            <Input placeholder="Input" color="green"/>
            <Text color="black">Kürzel:</Text>
            <Input placeholder="Input" color="green"/>
            <Text color="black">Studiengang:</Text>
            <Input placeholder="Input" color="green"/>
            <Text color="black">Lehrveranstaltung:</Text>
            <Input placeholder="Input" color="green"/>
            <Text color="black">Dozent:</Text>
            <Input placeholder="Input" color="green"/>       
        </Box>
    )
}
export default StudyProgram;