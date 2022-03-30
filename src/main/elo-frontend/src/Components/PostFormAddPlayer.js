import React, {Component} from "react";
import axios from "axios";
import {useNavigate} from "react-router-dom";
import './button.css';

var userName = "";
var userID;
let navigate;

export function PostFormAddPlayer(name){
  // console.log(name);
  userName = name.name;
  // console.log(userName.name + "=");
  navigate = useNavigate();
  return(<PostFormAddPlayerTemp />)
}

class PostFormAddPlayerTemp extends Component{
  
  
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
      userID = response.data.data.Adding.id;
      console.log(userID);
      axios.post(`http://localhost:8080/api/user/addPlayer/${userID}`, userName.name + "=")
      navigate("/players", {state : {name:userName.name}});
    })
    e.preventDefault();
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
            <button className = {((this.state.name.length > 0) && (this.state.elo.length > 0 && typeof this.state.elo !== "function")) ? "button" : "invalid-button"} type = 'submit' disabled={!(this.state.name.length > 0) || !(this.state.elo.length > 0)}>
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

// export function PostFormAddPlayer(){

//   const [playerName, setPlayerName] = useState();
//   const [playerElo, setPlayerElo] = useState(0);
//   const [allowButton, setAllowButton] = useState(false);

//   const handleSubmit = (e) => {
//         // e.preventDefault();

//         const data = {
//           id: '',
//           name: playerName,
//           elo: playerElo
//         }

//         axios.post('http://localhost:8080/api/player/add', data)
//         .then(response => {
//           console.log(response.data);
//         })
//       }

//   const handleNameChange = (event) =>{
//     setPlayerName(event.target.value)
//     console.log("name change");
//     console.log(playerName);
//     console.log(playerElo);
//     if(playerName !== '' && playerElo !== 0){
//       setAllowButton(true);
//     }
//     else{
//       setAllowButton(false);
//     }
//   }

//   const handleEloChange = (event) =>{
//     console.log(typeof parseInt(event.elo))
//     setPlayerElo(parseInt(event.elo))
//     console.log("elo change");
//     console.log(playerName);
//     console.log(playerElo);
//     if(playerName !== '' && playerElo !== 0){
//       setAllowButton(true);
//     }
//     else{
//       setAllowButton(false);
//     }
//   }
//   return(
//     <div className = "full-table-players">
//       <form onSubmit = {handleSubmit}>
//         <div className = "label-and-input">
//           <div>
//             <label>Name</label>
//             <input 
//               type = 'text' 
//               value = {playerName}
//               onChange = {e => handleNameChange(e)}></input>
//           </div>
//           <div>
//             <label>Elo</label>
//             <input 
//               onKeyPress={(event) => {
//                 if (!/[0-9]/.test(event.key)) {
//                   event.preventDefault();
//                 }
//               }}
//               type = 'text'
//               onChange = {e => handleEloChange({elo: e.target.value.replace(/\D/,'')})}></input>
//           </div>
//         </div>
//         <div>
//           <button className = {allowButton ? "button" : "invalid-button"} type = 'submit' disabled={!allowButton}>
//             <span className="text">
//               Add Player
//             </span>
//             </button>
//         </div>
//       </form>
//     </div>
//   )
// }