import React, {useState, useMemo, useEffect} from 'react'
import { useTable} from 'react-table'
import {COLUMNS_TEAMS_1, COLUMNS_TEAMS_2} from './Columns'
import axios from "axios";
import Header from './Header';
import './table.css';
import useSWR from 'swr'
// import useSWRImmutable from 'swr/immutable'
import { PostResults } from './PostResults';

export function ListTeamsBoth () {

    const fetcher = url => axios.get(url).then(res => res.data)
    
    const [sleeping, setSleeping] = useState(true)

    const { data, error } = useSWR(sleeping ? null : 'https://team-balancer-elo.wl.r.appspot.com/api/player/balanceTeams', fetcher, {revalidateOnFocus: false});

    useEffect(() => {
        setTimeout(() => {
            setSleeping(false)
        }, 500)
    }, [])


    if (error) console.log("lol");
    if (!data) return <div>loading...</div>

    const newData = data;

    return(
        <>
        <Header />
        <div className= "container">
            <div className = "team-number">
                1
            </div>
            <div className="left-team-1">
                <ListTeams1 props={newData[0]} />
            </div>
            <div className = "elo-diff">
                <p>Elo Difference: {newData[0].eloDifference}</p>
            </div>
            <div className="right-team-2">
                <ListTeams2 props={newData[0]} />
            </div>
            <PostResults index = {0}/>
        </div>
        <div className= "container">
            <div className = "team-number">
                2
            </div>
            <div className="left-team-1">
                <ListTeams1 props={newData[1]} />
            </div>
            <div className = "elo-diff">
                <p>Elo Difference: {newData[1].eloDifference}</p>
            </div>
            <div className="right-team-2">
                <ListTeams2 props={newData[1]} />
            </div>
            <PostResults index = {1}/>
        </div>
        <div className= "container">
            <div className = "team-number">
                3
            </div>
            <div className="left-team-1">
                <ListTeams1 props={newData[2]} />
            </div>
            <div className = "elo-diff">
                <p>Elo Difference: {newData[2].eloDifference}</p>
            </div>
            <div className="right-team-2">
                <ListTeams2 props={newData[2]} />
            </div>
            <PostResults index = {2}/>
        </div>
        <div className= "container">
            <div className = "team-number">
                4
            </div>
            <div className="left-team-1">
                <ListTeams1 props={newData[3]} />
            </div>
            <div className = "elo-diff">
                <p>Elo Difference: {newData[3].eloDifference}</p>
            </div>
            <div className="right-team-2">
                <ListTeams2 props={newData[3]} />
            </div>
            <PostResults index = {3}/>
        </div>
        <div className= "container-special">
            <div className = "team-number">
                5
            </div>
            <div className="left-team-1">
                <ListTeams1 props={newData[4]} />
            </div>
            <div className = "elo-diff">
                <p>Elo Difference: {newData[4].eloDifference}</p>
            </div>
            <div className="right-team-2">
                <ListTeams2 props={newData[4]} />
            </div>
            <PostResults index = {4}/>
        </div>
        </>
    )
}   

export function ListTeams1 (props){
    
    const [pProfiles, setPlayerProfiles] = useState([]);
    const [listTitle, setListTitle] = useState("");

    useEffect(() => {
        setPlayerProfiles(props.props.team1);
        setListTitle("Team 1 (" + props.props.sumEloT1 + ")");
    }, [props]);
    
    
    const columns = useMemo(() => COLUMNS_TEAMS_1, [])
    const data = pProfiles;

    const tableInstance = useTable({
        columns: columns,
        data: data
    })

    const{ getTableProps, getTableBodyProps, headerGroups, rows, prepareRow} = tableInstance

    headerGroups[0].headers[0].Header = listTitle;
    
    return (
        <table {...getTableProps()}>
            <thead>
                {headerGroups.map((headerGroup) => (
                    <tr {...headerGroup.getHeaderGroupProps()}>
                        {headerGroup.headers.map((column) => (
                            <th {...column.getHeaderProps()}>{column.render('Header')}</th>
                        ))}
                    </tr>
                ))}
            </thead>
            <tbody {...getTableBodyProps()}>
                {rows.map(row => {
                    prepareRow(row);
                    return (
                        <tr {...row.getRowProps()}>
                            {row.cells.map((cell) => {
                                return <td {...cell.getCellProps()}>{cell.render('Cell')}</td>;
                            })}
                        </tr>
                    );
                })}
            </tbody>
        </table>
    )
}

export function ListTeams2 (props) {
    const [pProfiles_2, setPlayerProfiles_2] = useState([]);
    const [listTitle, setListTitle] = useState("");
    
    useEffect(() => {
        setPlayerProfiles_2(props.props.team2)
        setListTitle("Team 2 (" + props.props.sumEloT2 + ")");
    }, [props]);


    const columns_2 = useMemo(() => COLUMNS_TEAMS_2, [])
    
    const tableInstance_2 = useTable({
        columns: columns_2,
        data: pProfiles_2
    })

    const{ getTableProps, getTableBodyProps, headerGroups, rows, prepareRow} = tableInstance_2
    
    headerGroups[0].headers[0].Header = listTitle;
    return (
        <table {...getTableProps()}>
            <thead>
                {headerGroups.map((headerGroup) => (
                    <tr {...headerGroup.getHeaderGroupProps()}>
                        {headerGroup.headers.map( (column) => (
                                <th {...column.getHeaderProps()}>{column.render('Header')}</th>
                            ))
                        }
                    </tr>
                ))}
            </thead>
            <tbody {...getTableBodyProps()}>
                {rows.map(row => {
                        prepareRow(row)
                        return (
                            <tr {...row.getRowProps()}>
                                {row.cells.map( (cell) => {
                                        return <td {...cell.getCellProps()}>{cell.render('Cell')}</td>
                                    })
                                }
                            </tr>
                        )
                    })
                }
            </tbody>
        </table>
    )
}
