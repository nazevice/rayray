import { Box, Button, Modal } from "@mui/material"

const FormModal = ({ open, handleClose, handleSubmit, children }) => {
    const handleFormSubmit = (event) => {
        event.preventDefault();
        handleClose();
        handleSubmit(event);
    };
    return (
        <Modal open={open} onClose={handleClose} sx={{ display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
            <Box
                height="auto"
                width="50%"
                bgcolor="white"
                padding={2}
                m={2}
                component="form"
                onSubmit={handleFormSubmit}
            >
                {children}

                <Button
                    type="submit"
                    variant="contained"
                    color="primary"
                    fullWidth
                >
                    Submit
                </Button>
            </Box>
        </Modal>
    )
}
export default FormModal;