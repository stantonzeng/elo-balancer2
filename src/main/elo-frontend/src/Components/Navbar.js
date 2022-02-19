
import {BrowserRouter,Routes,Route} from "react-router-dom";
import React from 'react'
import Header from './Header';
import { BasicTable } from './BasicTable';
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

function Navbar() {
    return(
        <BrowserRouter>
            <Routes>
                <Route path = '/:page' element = {<Header/>} />
                <Route path='/' element={<Header/>} />

                <Route exact path='home' element = {<Home/>}/>
                <Route exact path='players' element = {<BasicTable/>}/>
                <Route exact path='about' element = {<About/>}/>
            </Routes>
        </BrowserRouter>
    )
}

export default Navbar
