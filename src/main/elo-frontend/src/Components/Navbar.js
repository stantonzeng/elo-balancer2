
import {BrowserRouter,Routes,Route} from "react-router-dom";
import React from 'react'
import Header from './Header';
import { BasicTable } from './BasicTable';
import { ListTeamsBoth } from "./ListTeams";
import '../App.css';

const Home = () => (
    <><Header /><div>
        <h2>Home</h2>
    </div></>
);
  
const About = () => (
    <><Header /><div>
        <h2>About</h2>
    </div></>
);

export default function Navbar() {
    return(
        <BrowserRouter>
            <Routes>
                <Route path = '/:page' element = {<Header/>} />
                <Route path='/' element={<Home/>} />

                <Route exact path='' element = {<Home/>}/>
                <Route exact path='home' element = {<Home/>}/>
                <Route exact path='players' element = {<BasicTable/>}/>
                <Route exact path='about' element = {<About/>}/>
                <Route exact path="listTeams" element = {<ListTeamsBoth/>}/>
                <Route path="*" element={<Home />} />
            </Routes>
        </BrowserRouter>
    )
}
