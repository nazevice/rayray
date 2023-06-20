import React from 'react';
import { Link } from 'react-router-dom';
import { Box } from 'dracula-ui';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';

const Header = () => {
  return (
    <Box color="animated" height='xxxs' width='full'>
      <Navbar>
        <Navbar.Brand as={Link} to="/">Steinbeis</Navbar.Brand>
        <Navbar.Toggle aria-controls="basic-navbar-nav" />
        <Nav className="me-auto">
            <Link to="/dashboard" className="nav-link">Dashboard</Link>
            <Link to="/studienprogramm" className="nav-link">Dashboard</Link>
        </Nav>
      </Navbar> 
    </Box>
  );
};

export default Header;
