import './App.css';
import React, {useState, useEffect, Component} from "react";
import axios from "axios";
import PostFormAddPlayer from './Components/PostFormAddPlayer';
import PostResults from './Components/PostResults';
import GetPlayerProfiles from './Components/GetPlayerProfiles';



function App() {
  return (
    <div className="App">
      {/* <GetPlayerProfiles /> */}
      {/* <PostFormAddPlayer /> */}
      <PostResults />
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