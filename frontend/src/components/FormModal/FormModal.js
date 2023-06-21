import { Box, Modal } from "@mui/material"

const FormModal = ({open, handleClose, handleSubmit, children}) => {
    return(
            <Modal open={open} onClose={handleClose} sx={{ display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
                <Box 
                    height="auto" 
                    width="50%" 
                    bgcolor="white" 
                    padding={2} 
                    m={2}
                    component="form" 
                    onSubmit={handleSubmit}
                >
                    {children}
                </Box>
            </Modal>
    )
}
export default FormModal;