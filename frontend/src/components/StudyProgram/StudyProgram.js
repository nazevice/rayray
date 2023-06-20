import React from 'react';
import { Box, Input, Text } from 'dracula-ui';

const StudyProgram = () => {
    return (
        <Box color="" width='md'>
            <Text color="black" weight="bold">Name:</Text>
            <Input placeholder="Input"/>
            <Text color="black" weight="bold">Kürzel:</Text>
            <Input placeholder="Input" />
            <Text color="black" weight="bold">Studiengang:</Text>
            <Input placeholder="Input" />
            <Text color="black" weight="bold">Lehrveranstaltung:</Text>
            <Input placeholder="Input" />
            <Text color="black" weight="bold">Dozent:</Text>
            <Input placeholder="Input" />       
        </Box>
    )
}
export default StudyProgram;