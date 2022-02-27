
import {BrowserRouter,Routes,Route, useLocation, useNavigate, useParams} from "react-router-dom";
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

function withRouter(Component) {
    function ComponentWithRouterProp(props) {
        let location = useLocation();
        let navigate = useNavigate();
        let params = useParams();
        return (
        <Component
            {...props}
            router={{ location, navigate, params }}
        />
        );
    }

    return ComponentWithRouterProp;
}

function Navbar() {
    const WithRouterAppList = withRouter(ListTeamsBoth);
    return(
        <BrowserRouter>
            <Routes>
                <Route path = '/:page' element = {<Header/>} />
                <Route path='/' element={<Header/>} />

                <Route exact path='' element = {<Home/>}/>
                <Route exact path='home' element = {<Home/>}/>
                <Route exact path='players/*' element = {<BasicTable/>}/>
                <Route exact path='about' element = {<About/>}/>
                <Route exact path="listTeams" element = {<WithRouterAppList/>}/>
                <Route path="*" element={<Home />} />
            </Routes>
        </BrowserRouter>
    )
}

export default Navbar
