import './App.css';
import React, {useState, useEffect, Component} from "react";
import PostFormAddPlayer from './Components/PostFormAddPlayer';
import PostResults from './Components/PostResults';
import GetPlayerProfiles from './Components/GetPlayerProfiles';
import { BasicTable } from './Components/BasicTable';
import { EloSum1, ListTeams1, ListTeams2 } from './Components/ListTeams';
import Navbar from './Components/Navbar';

//npm install react-table
//npm install react-select

function App() {
  return (
    <div className="App">
      {/* <GetPlayerProfiles /> */}
      {/* <PostFormAddPlayer />
      <BasicTable />
      <PostResults /> */}
      {/* <ListTeams1 /> */}
      {/* <EloSum1 /> */}
      {/* <ListTeams2 /> */}
      <Navbar />
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