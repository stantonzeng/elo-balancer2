import './App.css';
import React from "react";
import Navbar from './Components/Navbar';

function App() {
  return (
    <div className="App">
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