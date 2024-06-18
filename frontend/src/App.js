import './App.css';
import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import LandingPage from './components/LandingPage';
import HomeScreen from './components/HomeScreen';
import './App.css';

function App() {

  return (
    <Router>
      <Routes>
        <Route exact path="/" element={<LandingPage/>} />
        <Route exact path="/home" element={<HomeScreen/>} />
      </Routes>
    </Router> 
  );
}

export default App;