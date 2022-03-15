import React, {useState} from 'react'
import Select from 'react-select';
import axios from "axios";
import './win.css';
import { useNavigate } from 'react-router';

const options = [
  {value: 0, label: 'Team 1' },
  {value: 1, label: 'Team 2'}
];

export const PostResults = (index) => {
  const [teamNumb, setTeamNumb] = useState();
  let navigate = useNavigate();

  const handleSubmit = (e) =>{
    // e.preventDefault();
    console.log(`http://localhost:8080/api/player/win/${index.index}`);
    axios.post(`http://localhost:8080/api/player/win/${index.index}`, teamNumb);
    // window.location = "/players";
    navigate("/players");
  }

  return(
    <form onSubmit = {handleSubmit}>
      <div className = "select-button">
      <Select 
        options = {options} 
        value = {teamNumb}
        onChange = {value => setTeamNumb(value)}
        placeholder = "Select Winning Team"
      />
      </div>
      <button type = 'submit'>Submit</button>
    </form>
  )
}