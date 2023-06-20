import React from "react";
import {Box, Input, Text} from 'dracula-ui'

const Semester = () => {

    return (
        <Box color ="" width="md">
            <Text color="black" weight="bold">Beginn des Semesters:</Text>
            <Input placeholder="Date" type="date" />
            <Text color="black" weight="bold">Ende des Semesters:</Text>
            <Input placeholder="Date" type="date" />
            <Text color="black" weight="bold">Nummer des Semesters:</Text>
            <Input placeholder="Input" type="number"/>
            <Text color="black" weight="bold"> Name der Semesters:</Text>
            <Input placeholder="Input" />
        </Box>

    )
}
export default Semester;