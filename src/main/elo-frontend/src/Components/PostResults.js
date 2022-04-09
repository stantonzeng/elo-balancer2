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
    console.log(`https://team-balancer-elo.wl.r.appspot.com/api/player/win/${index.index}`);
    axios.post(`https://team-balancer-elo.wl.r.appspot.com/api/player/win/${index.index}`, teamNumb);
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