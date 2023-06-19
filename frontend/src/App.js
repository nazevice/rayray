import React from 'react';
import './App.css';
import 'dracula-ui/styles/dracula-ui.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import Header  from './components/Header/Header';
import Content from './components/Content/Content';
import Footer from './components/Footer/Footer';
function App() {
  return (
    <div className="App">
        <Header />
        <Content />
        <Footer />
    </div>
  );
}

export default App;
