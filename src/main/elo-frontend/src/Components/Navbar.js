import {BrowserRouter,Routes,Route} from "react-router-dom";
import React from 'react'
import Header from './Header';
import { BasicTable } from './BasicTable';
import { ListTeamsBoth } from "./ListTeams";
import '../App.css';
import './Text.css';
import example_test from '../balancer_gifs/example_test_2.gif'
import example_balance from '../balancer_gifs/balance_teams.gif'
import { Login } from "./Login";

const Home = () => (
    <><Header />
    <div className = "Home_About">
        <p className = "norm"> This is a n x n team balancer. You can create your own datatable of players and it will output the 
            5 best different teams from the players you selected.</p>
        
        <img src = {example_test} alt = "Example 1"/>
        <p className = "norm"> You first go to the players tab and create a new table of players. You can reuse this table later.
            You can add players based on their names and assign them a baseline elo. In order to select the players
            that are going to be playing, you can just simply click on them. You will be allowed to balance the teams
            when at least 4 or more players are selected.
        </p>
        <img src = {example_balance} alt = "Example 2"/>
        <p className = "norm"> Once the teams are balanced, you can play your game with whatever team you select. You can input which team
        wins, and the balancer will adjust the elos accordingly</p>
    </div></>
);
  
const About = () => (
    <><Header />
        <div className = "Home_About">
        <p className = "norm"> This is a passion project. I have no background in front end or back end design, so this was my first time trying a project of 
            this size. If there are any egregious bugs or problems, you can message me through github (or fix them if you can). If you are also really good at system
            design, then feel free to modify the code for this site! It can be optimized greatly.
            
        </p>
        <a href="https://github.com/stantonzeng/elo-balancer2">Github</a>
        </div>
        </>
);

export default function Navbar() {
    return(
        <BrowserRouter>
            <Routes>
                <Route path = '/:page' element = {<Header/>} />
                <Route path='/' element={<Home/>} />

                <Route exact path='home' element = {<Home/>}/>
                <Route exact path='login' element = {<Login/>}/>
                <Route exact path='players' element = {<BasicTable/>}/>
                <Route exact path='about' element = {<About/>}/>
                <Route exact path="listTeams" element = {<ListTeamsBoth/>}/>
                <Route path="*" element={<Home />} />
            </Routes>
        </BrowserRouter>
    )
}
