import axios from "axios";
import React, {useState, useEffect} from 'react'
import Header from "./Header";
import './button.css';
import './login.css';
import {useNavigate} from "react-router-dom";



export function Login(){

    
    const one = "Could not find data table, try again";
    const two = "Table name already taken, try again";
    
    
    const [dataname, setDataname] = useState('');
    const [create, setCreate] = useState(false);
    const [doesDataExist, setDoesDataExist] = useState(false);
    const [outLog, setOutLog] = useState(' ');
    // const [user, setUser] = useState();
    const [lazyWayOut, setLazyWayOut] = useState(0);

    let navigate = useNavigate();
    
    useEffect(() => {
        if(create && !doesDataExist) {
            setCreate(false);
            // console.log('https://team-balancer-elo.wl.r.appspot.com/api/user/addString');
            console.log(dataname);
            var newv = dataname.replace(/ /g, '_');
            axios.post('https://team-balancer-elo.wl.r.appspot.com/api/user/addString', newv)
            FindDatatable(newv);
        }
        else if(create && doesDataExist){
            setOutLog(two);
        }
        else if(!create && doesDataExist){
            var newv = dataname.replace(/ /g, '_');
            FindDatatable(newv);
        }
        else{
            if(lazyWayOut > 0) setOutLog(one);
        }
    }, [lazyWayOut]);

    const CheckName = async () => {
        var newv = dataname.replace(/ /g, '_');
        await axios.get(`https://team-balancer-elo.wl.r.appspot.com/api/user/check/${newv}=`).then(res => {console.log(res)
        setDoesDataExist(res.data)
        setLazyWayOut(lazyWayOut+1)});
    }

    //axios.get(`https://team-balancer-elo.wl.r.appspot.com/api/user/get/${dataname}`).then(res => {console.log(res)});
    //axios.get(`https://team-balancer-elo.wl.r.appspot.com/api/user/check/${dataname}`).then(res => {console.log(res)}) 
    const FindDatatable = (newv) =>{
        console.log("Getting datatable")
        // axios.get(`https://team-balancer-elo.wl.r.appspot.com/api/user/get/${newv}=`).then(res => {
        //     console.log(res)});
            //setUser(res)
        
        navigate("/players", {state : {name:newv}});
    }
    return(
        
        <><Header />
        
        <div>
            <p className = "invalid">{outLog}</p>
            <label>Datatable Name</label>
            <input
                type="text"
                value = {dataname}
                onChange={e => setDataname(e.target.value)}></input>
        </div>
        <button onClick = {async () => {
        setCreate(false);
        await CheckName();
        if(!doesDataExist) setOutLog(one);
        }}
        className = {dataname.length > 0 ? "button" : "invalid-button"} type = 'submit' disabled={!(dataname.length > 0)}>
            <span className="text">
                Use Datatable
            </span>
        </button>

        <button onClick = {async () => {
        setCreate(true);
        await CheckName();
        }}
        className = {dataname.length > 0 ? "button" : "invalid-button"} type = 'submit' disabled={!(dataname.length > 0)}>
            <span className="text">
                Create Datatable
            </span>
        </button>
        </>


    )
    
}