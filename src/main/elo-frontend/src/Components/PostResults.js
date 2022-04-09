import React, {useState} from 'react'
import Select from 'react-select';
import axios from "axios";
import './win.css';
import { useNavigate, useLocation} from 'react-router';

const options = [
  {value: 0, label: 'Team 1' },
  {value: 1, label: 'Team 2'}
];

export const PostResults = (index) => {
  const {state} = useLocation();
  const [teamNumb, setTeamNumb] = useState();
  const [allowButton, setAllowButton] = useState(false);

  let navigate = useNavigate();

  const handleSubmit = (e) =>{
    console.log(`https://8080-0610b5c5-a6b5-4694-9cd9-b58bba0c3d73.cs-us-west1-ijlt.cloudshell.dev/api/player/win/${index.index}`);
    axios.post(`https://8080-0610b5c5-a6b5-4694-9cd9-b58bba0c3d73.cs-us-west1-ijlt.cloudshell.dev/api/player/win/${index.index}`, teamNumb);
    let newv = state.name;
    navigate("/players", {state : {name:newv}});
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