import React from 'react';
import { Link } from 'react-router-dom';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import { Box } from '@mui/material';

const Header = () => {
  return (
    <Box bgcolor="primary.main">
      <Navbar>
        <Navbar.Brand as={Link} to="/">LectureMaster</Navbar.Brand>
        <Navbar.Toggle aria-controls="basic-navbar-nav" />
        <Nav className="me-auto">
            <Link to="/dashboard" className="nav-link">Dashboard</Link>
            <Link to="/studienprogramm" className="nav-link">StudyProgram</Link>
            <Link to="/vorlesung" className="nav-link">Lecture</Link>
            <Link to="/semester" className="nav-link">Semester</Link>
            <Link to="/dozent" className="nav-link">Dozent</Link>
            <Link to="/studienklasse" className="nav-link">StudyClass</Link>
        </Nav>
      </Navbar> 
    </Box>
  );
};

export default Header;
