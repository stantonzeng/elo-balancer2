import React, {useState, useEffect, Component} from "react";
import axios from "axios";

const GetPlayerProfiles = () => {

    const [pProfiles, setPlayerProfiles] = useState([]);
    //pProfiles is an object, setPlayerProfiles is a function that uses
    //pProfiles, and useState is an array that holds all of the pProfiles.

    const fetchPlayerProfiles = () =>{
      axios.get('http://https://8080-0610b5c5-a6b5-4694-9cd9-b58bba0c3d73.cs-us-west1-ijlt.cloudshell.dev/api/player/full_list').then(res => {
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