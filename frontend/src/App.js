import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Header from './components/Header/Header';
import Content from './components/Content/Content';
import Dashboard from './components/Dashboard/Dashboard'
import StudyProgram from './components/StudyProgram/StudyProgram';
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
        </Routes>
        
        <Footer />
      </div>
    </Router>
  );
}
export default App;
