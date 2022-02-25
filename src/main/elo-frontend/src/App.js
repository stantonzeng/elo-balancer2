import './App.css';
import React, {useState, useEffect, Component} from "react";
import Navbar from './Components/Navbar';

//npm install react-table
//npm install react-select

function App() {
  return (
    <div className="App">
      <Navbar />
      {/* <BasicTable /> */}
    </div>
  );  
}

export default App;















/*
Comment Hell:

const AddPlayerProfiles = () => {
  const AddP = () =>{
    const player = {};
    axios.post('http://localhost:8080/api/player/add', player)
      .then(response => setPlayerId(response.data.id));
  }

  useEffect(() => {
    AddP();
  }, []);
}



---------------------------------------------------------------




*/