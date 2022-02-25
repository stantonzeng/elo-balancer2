import React, {Component} from "react";
import axios from "axios";
import '../App.css';

class PostFormAddPlayer extends Component{

  constructor(props){
    super(props);

    this.state = {
      id: '',
      name: '',
      elo: Number
    }
  }

  handleChangeName = (e) =>{
    this.setState({
      [e.target.name]: e.target.value
    })
  }
  handleChangeElo = (e) =>{
    this.setState({
      [e.target.elo]: e.target.value
    })
  }

  handleSubmit = (e) => {
    axios.post('http://localhost:8080/api/player/add', this.state)
    .then(response => {
      console.log(response.data);
    })
  }

  render(){
    const{elo, name} = this.state;
    return(
      <div className = "full-table-players">
        <form onSubmit = {this.handleSubmit}>
          <div className = "label-and-input">
            <div>
              <label>Name</label>
              <input 
                type = 'text' 
                name = 'name' 
                value = {name}
                onChange = {this.handleChangeName}></input>
            </div>
            <div>
              <label>Elo</label>
              <input 
                onKeyPress={(event) => {
                  if (!/[0-9]/.test(event.key)) {
                    event.preventDefault();
                  }
                }}
                name = 'elo' 
                value = {elo}
                onChange = {event => this.setState({elo: event.target.value.replace(/\D/,'')})}></input>
            </div>
          </div>
          <div>
            <button className = {(!(this.state.name.length > 0) || !(this.state.elo.length > 0)) ? "invalid-button" : "button"} type = 'submit' disabled={!(this.state.name.length > 0) || !(this.state.elo.length > 0)}>
              <span className="text">
                Add Player
              </span>
              </button>
          </div>
        </form>
      </div>
    )
  }
}

export default PostFormAddPlayer