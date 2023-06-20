import React from "react";
import {Box, Input, Text} from 'dracula-ui'

const Dozent = () => {

    return (
        <Box color ="" width="md">
            <Text color="black" weight="bold">Vorname:</Text>
            <Input placeholder="Input" />
            <Text color="black" weight="bold">Nachname:</Text>
            <Input placeholder="Input" />
            <Text color="black" weight="bold">E-Mail:</Text>
            <Input placeholder="Input" type="email" />
        </Box>

    )
}
export default Dozent;