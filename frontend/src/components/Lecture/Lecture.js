import React from "react";
import {Box, Input, Text} from 'dracula-ui'

const Lecture = () => {

    return (
        <Box color ="" width="md">
            <Text color="black" weight="bold">Name der Lehrveranstaltung:</Text>
            <Input placeholder="Input" />
            <Text color="black" weight="bold">Name des Moduls:</Text>
            <Input placeholder="Input" />
            <Text color="black" weight="bold">Vorlesungszeit:</Text>
            <Input placeholder="Input" type="time" />
            <Text color="black" weight="bold">Datum der Vorlesung:</Text>
            <Input placeholder="Date" type="date" />
            <Text color="black" weight="bold">Studiengang:</Text>
            <Input placeholder="Input"/>
        </Box>

    )
}
export default Lecture;