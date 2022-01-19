import './App.css';
import React, {useState, useEffect, Component} from "react";
import axios from "axios";

const PlayerProfiles = () => {

  const [pProfiles, setPlayerProfiles] = useState([]);
  //pProfiles is an object, setPlayerProfiles is a function that uses
  //pProfiles, and useState is an array that holds all of the pProfiles.

  const fetchPlayerProfiles = () =>{
    axios.get("http://localhost:8080/api/player/list_test").then(res => {
      // console.log(res);
      console.log(res.data);
      setPlayerProfiles(res.data);
      
    });
  }

  useEffect(() => {
    fetchPlayerProfiles();
  }, []); //Anytime the array inside is updated, it calls the useEffect again

  return pProfiles.map((player, index) => {
    return (
      <div key = {index}>
        <p>{player.id}</p>
        <p>{player.elo}</p>
        <p>{player.name}</p>
      </div>
    )
  })
}

// const AddPlayerProfiles = () => {
//   const AddP = () =>{
//     const player = {};
//     axios.post('http://localhost:8080/api/player/add', player)
//       .then(response => setPlayerId(response.data.id));
//   }

//   useEffect(() => {
//     AddP();
//   }, []);
// }

class PostForm extends Component{

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

function App() {
  return (
    <div className="App">
      <PlayerProfiles />
      <PostForm />
    </div>
  );
}

export default App;