import { Box } from 'dracula-ui';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';

const Header = () => {
    return(
        <header>
                <Box color="animated" height='xxxs' width='full'>
                    <Navbar>
                        <Navbar.Brand href="#home">React-Bootstrap</Navbar.Brand>
                        <Navbar.Toggle aria-controls="basic-navbar-nav" />
                        <Nav className="me-auto">
                            <Nav.Link href="#link">Link</Nav.Link>
                        </Nav>
                    </Navbar> 
                </Box>      
        </header>
    )
}
export default Header;