import React from "react";
import {Box, Input, Text} from 'dracula-ui'

const StudyClass = () => {

    return (
        <Box color ="" width="md">
            <Text color="black" weight="bold">Name Studienklasse:</Text>
            <Input placeholder="Input"/>
            <Text color="black" weight="bold">Beginn Studenklasse:</Text>
            <Input placeholder="Date" type="date" />
            <Text color="black" weight="bold">Ende Studenklasse:</Text>
            <Input placeholder="Date" type="date" />

        </Box>

    )
}
export default StudyClass;