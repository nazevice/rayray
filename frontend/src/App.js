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
import Footer from './components/Footer/Footer';
import 'dracula-ui/styles/dracula-ui.css';
import 'bootstrap/dist/css/bootstrap.min.css';

function App() {
  return (
    <Router>
      <div className="App">
        <Header />
        <Routes>
          <Route exact path="/" element={<Content />} />
          <Route path="/dashboard" element={<Dashboard />} />
          <Route path="/studienprogramm" element={<StudyProgram />} />
          <Route path="/vorlesung" element={<Lecture/>} />
          <Route path="/Semester" element={<Semester/>} />
          <Route path="/dozent" element={<Dozent/>} />
          <Route path="/studienklasse" element={<StudyClass/>} />
        </Routes>
        
        <Footer />
      </div>
    </Router>
  );
}
export default App;
