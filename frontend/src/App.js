import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Header from './components/Header/Header';
import Content from './components/Content/Content';
import Dashboard from './components/Dashboard/Dashboard'
import StudyProgram from './components/StudyProgram/StudyProgram';
import Lecture from './components/Lecture/Lecture';
import Semester from './components/Semester/Semester';
import Dozent from './components/Dozent/Dozent';
import StudyClass from './components/StudyClass/StudyClass';
import Impressum from './components/Impressum/Impressum';
import Datenschutz from './components/Datenschutz/Datenschutz';
import Footer from './components/Footer/Footer';
import 'dracula-ui/styles/dracula-ui.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import { Box, ThemeProvider, createTheme } from '@mui/material';
import { LocalizationProvider } from '@mui/x-date-pickers';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs'

const theme = createTheme({
  palette: {
    primary: {
      main: "#27374D", //#27374D
      light: "#DDE6ED"
    },
    secondary: {
      main: "#fff"
    },
    custom: {
      fontMain: "#181818",
      fontSecondary: "#fff"
    }
  },
});


function App() {
  return (
    <ThemeProvider theme={theme}>
      <LocalizationProvider dateAdapter={AdapterDayjs}>
      <Router>
        <Box sx={{ backgroundColor: "primary.light", display: 'flex', flexDirection: 'column', minHeight: '100vh', overflowX: "hidden" }}>
          <Header />
          <Box flex="1">
          <Routes>
            <Route exact path="/" element={<Content />} />
            <Route path="/dashboard" element={<Dashboard />} />
            <Route path="/studienprogramm" element={<StudyProgram />} />
            <Route path="/vorlesung" element={<Lecture />} />
            <Route path="/Semester" element={<Semester />} />
            <Route path="/dozent" element={<Dozent />} />
            <Route path="/studienklasse" element={<StudyClass />} />
            <Route path="/impressum" element={<Impressum />} />
            <Route path="/datenschutz" element={<Datenschutz />} />
          </Routes>
          </Box>
          <Footer />
          </Box>
          </Router>
    </LocalizationProvider>
    </ThemeProvider>
  )
}
export default App;
