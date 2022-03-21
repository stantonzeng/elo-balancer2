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
  const [allowButton, setAllowButton] = useState(false);

  let navigate = useNavigate();

  const handleSubmit = (e) =>{
    console.log(`http://localhost:8080/api/player/win/${index.index}`);
    axios.post(`http://localhost:8080/api/player/win/${index.index}`, teamNumb);
    navigate("/players");
  }

  const handleChange = (value) =>{
    setAllowButton(true);
    setTeamNumb(value);
  }

  return(
    <form onSubmit = {handleSubmit}>
      
      <div className = "select-button">
      <Select 
        options = {options} 
        value = {teamNumb}
        onChange = {handleChange}
        placeholder = "Select Winning Team"
      />
      <button className = {allowButton ? "button-2" : "invalid-button-2"} type = 'submit' disabled={!allowButton}>
        Submit
        </button>
      </div>
    </form>
  )
}