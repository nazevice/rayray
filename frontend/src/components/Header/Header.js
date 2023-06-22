import React from 'react';
import { Link } from 'react-router-dom';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import { Box } from '@mui/material';

const Header = () => {
  return (
    <Box bgcolor="primary.main" sx={{ position: 'sticky', top: 0, zIndex: 100 }}>
      <Navbar>
        <Navbar.Brand as={Link} to="/" style={{fontWeight: 'bold', color: 'white', marginLeft: 24}}>LectureMaster</Navbar.Brand>
        <Navbar.Toggle aria-controls="basic-navbar-nav" />
        <Nav className="me-auto">
            <Link to="/dashboard" className="nav-link" style={{fontWeight: 'bold', color: 'white'}}>Dashboard</Link>
            <Link to="/semester" className="nav-link" style={{fontWeight: 'bold', color: 'white'}}>Semester</Link>
            <Link to="/studienprogramm" className="nav-link" style={{fontWeight: 'bold', color: 'white'}}>Studiengang</Link>
            <Link to="/studienklasse" className="nav-link" style={{fontWeight: 'bold', color: 'white'}}>Studienklasse</Link>
            <Link to="/vorlesung" className="nav-link" style={{fontWeight: 'bold', color: 'white'}}>Lehrveranstaltung</Link>
            <Link to="/dozent" className="nav-link" style={{fontWeight: 'bold', color: 'white'}}>Dozent</Link>
        </Nav>
      </Navbar> 
    </Box>
  );
};

export default Header;
