import React from "react";
import {Box, Input, Text} from 'dracula-ui'

const Semester = () => {

    return (
        <Box color ="" width="md">
            <Text color="black">Beginn des Semesters:</Text>
            <Input placeholder="Date" type="date" />
            <Text color="black">Ende des Semesters:</Text>
            <Input placeholder="Date" type="date" />
            <Text color="black">Nummer des Semesters:</Text>
            <Input placeholder="Input" type="number"/>
            <Text color="black">Name der Semesters:</Text>
            <Input placeholder="Input" />
        </Box>

    )
}
export default Semester;