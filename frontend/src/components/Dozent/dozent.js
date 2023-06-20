import React from "react";
import {Box, Input, Text} from 'dracula-ui'

const Dozent = () => {

    return (
        <Box color ="" width="md">
            <Text color="black">Vorname:</Text>
            <Input placeholder="Input" />
            <Text color="black">Nachname:</Text>
            <Input placeholder="Input" />
            <Text color="black">E-Mail:</Text>
            <Input placeholder="Input" type="email" />
        </Box>

    )
}
export default Dozent;