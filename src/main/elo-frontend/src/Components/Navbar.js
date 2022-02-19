
import {BrowserRouter,Routes,Route} from "react-router-dom";
import React from 'react'
import Header from './Header';
import { BasicTable } from './BasicTable';
import '../App.css';

const Home = () => (
    <div>
        <h2>Home</h2>
    </div>
);

const Players = () => (
    <div>
        <h2><Players /></h2>
    </div>
);
  
const About = () => (
    <div>
        <h2>About</h2>
    </div>
);

function Navbar() {
    return(
        <div className = "Navbar">
            <div className = "leftSide">
            <BrowserRouter>
                <Routes>
                    <Route path='/:page' element={<Header/>} />
                    <Route exact path='/' element={<Header/>} />

                    <Route exact path='' element = {<Home/>}/>
                    <Route exact path='/home' element = {<Home/>}/>
                    <Route exact path='/players' element = {<BasicTable />}/>
                    <Route exact path='/about' element = {<About/>}/>
                </Routes>
            </BrowserRouter>
            </div>
        </div>
    )
}

export default Navbar


{/* <a href = "/home">Home</a>
<a href = "/feedback">Feedback</a> */}
{/* <a href = "/aboutus">About Us</a> */}
{/* <a href = "/contact">Contact</a> */}