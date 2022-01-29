import React, {useState, useEffect, Component} from "react";
import axios from "axios";

const GetPlayerProfiles = () => {

    const [pProfiles, setPlayerProfiles] = useState([]);
    //pProfiles is an object, setPlayerProfiles is a function that uses
    //pProfiles, and useState is an array that holds all of the pProfiles.
  
    const fetchPlayerProfiles = () =>{
      axios.get('http://localhost:8080/api/player/full_list').then(res => {
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

export default GetPlayerProfiles