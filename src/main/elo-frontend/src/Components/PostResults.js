import React, {Component} from "react";
import Select from 'react-select';
import axios from "axios";

const options = [
  {value: 0, label: 'Team 1' },
  {value: 1, label: 'Team 2'}
];

class PostResults extends Component{
  state = { //Returns back in json form. Since we are looking for just the integer... we have to return it back in json form using the state
    teamNumb: Number
  }

  handleChange(v) {
    this.setState({ teamNumb: v.value})
  }

  handleSubmit = event => {
    console.log(this.state);

    axios.post('http://localhost:8080/api/player/win', this.state);
    
  }

  render(){
    const {selectedOption} = this.state;

    return(
      <form onSubmit = {this.handleSubmit}>
        <Select 
          options = {options} 
          value = {selectedOption}
          onChange = {value => this.handleChange(value)}
          placeholder = "Select Winning Team"
        />
        <button type = 'submit'>Submit</button>
      </form>
      
    )
  }
}
export default PostResults