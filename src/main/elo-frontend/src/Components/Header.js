import {Link, useParams} from "react-router-dom";
import React from 'react'
import './Header.css'
import '../App.css';

const HeaderLink = ({ page, selected }) => {
    const title = page.charAt(0).toUpperCase() + page.slice(1);
    let className = selected ? 'headerlink-no-link ' : '';
    className += 'headerlink-title';

    return (
        <Link to={`/${page}`} className='headerlink-title'>
            {title}
            <div className={selected ? 'headerlink-dot-active' : 'headerlink-dot'}>•</div>
        </Link>);
};

const Header = () => {
    const { page } = useParams().page || 'home';

    return (
        <div className='header'>
            <HeaderLink page='home' selected = {page === 'home'}/>
            <HeaderLink page='players' selected = {page === 'players'}/>
            <HeaderLink page='about' selected = {page === 'about'}/>
        </div>
    );
};

export default Header;