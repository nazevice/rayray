import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import { Box, useTheme } from '@mui/material';

const Header = () => {
  const theme = useTheme();
  const [hoverIndex, setHoverIndex] = useState(-1);

  const links = [
    { to: '/dashboard', label: 'Dashboard' },
    { to: '/studienprogramm', label: 'Studiengang' },
    { to: '/studienklasse', label: 'Studienklasse' },
    { to: '/vorlesung', label: 'Lehrveranstaltung' },
    { to: '/dozent', label: 'Dozent' }
  ];

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
            {links.map((link, index) => (
              <Link
                key={index}
                to={link.to}
                className="nav-link"
                style={{ 
                  color: hoverIndex === index ? '#526D82'  : theme.palette.custom.fontSecondary, 
                  paddingRight: link.to === '/dozent' ? "0px" : undefined
                }}
                onMouseEnter={() => setHoverIndex(index)}
                onMouseLeave={() => setHoverIndex(-1)}
              >
                {link.label}
              </Link>
            ))}
          </Nav>
        </Navbar.Collapse>
      </Navbar>
    </Box>
  );
};

export default Header;
