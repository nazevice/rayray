import React from 'react';
import { Link } from 'react-router-dom';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import { Box, useTheme } from '@mui/material';

const Header = () => {
  const theme = useTheme();
  return (
    <Box
  bgcolor={theme.palette.primary.main}
  sx={{
    position: 'sticky',
    top: 0,
    zIndex: 100,
  }}
>
  <Navbar expand="lg">
    <Navbar.Brand
      as={Link}
      to="/"
      style={{
        fontWeight: 'bold',
        color: theme.palette.custom.fontSecondary,
        marginLeft: 24,
      }}
    >
      LectureMaster
    </Navbar.Brand>
    <Navbar.Toggle 
      aria-controls="basic-navbar-nav" 
      style={{
        marginRight: "24px", 
        backgroundColor: theme.palette.secondary.main,
        borderColor: theme.palette.secondary.main
      }} 
    />
    <Navbar.Collapse style={{ justifyContent: 'flex-end', borderColor: theme.palette.secondary.main}}>
      <Nav style={{marginRight: "24px", marginLeft: "24px"}}>
        <Link
          to="/dashboard"
          className="nav-link"
          style={{ color: theme.palette.custom.fontSecondary }}
        >
          Dashboard
        </Link>
        <Link
          to="/studienprogramm"
          className="nav-link"
          style={{ color: theme.palette.custom.fontSecondary }}
        >
          Studiengang
        </Link>
        <Link
          to="/studienklasse"
          className="nav-link"
          style={{ color: theme.palette.custom.fontSecondary }}
        >
          Studienklasse
        </Link>
        <Link
          to="/vorlesung"
          className="nav-link"
          style={{ color: theme.palette.custom.fontSecondary }}
        >
          Lehrveranstaltung
        </Link>
        <Link
          to="/dozent"
          className="nav-link"
          style={{ color: theme.palette.custom.fontSecondary, paddingRight: "0px" }}
        >
          Dozent
        </Link>
      </Nav>
    </Navbar.Collapse>
  </Navbar>
</Box>

  );
};

export default Header;
