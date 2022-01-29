import React, {Component} from "react";
import axios from "axios";

class PostFormAddPlayer extends Component{

  constructor(props){
    super(props);

    this.state = {
      id: '',
      name: '',
      elo: Number
    }
  }

  handleChange = (e) =>{
    this.setState({
      [e.target.name]: e.target.value
    })
  }

  handleSubmit = (e) => {
    // e.preventDefault();
    axios.post('http://localhost:8080/api/player/add', this.state)
    .then(response => {
      console.log(response.data);
    })
    // console.log(this.state);
  }

  render(){
    const{elo, name} = this.state;
    return(
      <div>
        <form onSubmit = {this.handleSubmit}>
          <div>
            <label>Name</label>
            <input 
              type = 'text' 
              name = 'name' 
              value = {name}
              onChange = {this.handleChange}></input>
          </div>
          <div>
            <label>Elo</label>
            <input 
              type = 'number' 
              name = 'elo' 
              value = {elo}
              onChange = {this.handleChange}></input>
          </div>
          <div>
            <button type = 'submit'>Submit</button>
          </div>
        </form>
      </div>
    )
  }
}

export default PostFormAddPlayer