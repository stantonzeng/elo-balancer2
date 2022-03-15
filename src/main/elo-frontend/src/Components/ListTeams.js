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

    // console.log("fetching");
    const { data, error } = useSWR(sleeping ? null : 'http://localhost:8080/api/player/balanceTeams', fetcher, {revalidateOnFocus: false});

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
            <div className="left-team-1">
                <ListTeams1 props={newData[0]} />
            </div>
            <div className = "elo-diff">
                <p>Elo Difference: {newData[0].eloDifference}</p>
                <PostResults index = {0}/>
            </div>
            <div className="right-team-2">
                <ListTeams2 props={newData[0]} />
            </div>
        </div>
        </>
    )
}   

export function ListTeams1 (props){
    
    const [pProfiles, setPlayerProfiles] = useState([]);

    useEffect(() => {
        setPlayerProfiles(props.props.team1);
    }, [props]);
    
    
    const columns = useMemo(() => COLUMNS_TEAMS_1, [])
    const data = pProfiles;

    const tableInstance = useTable({
        columns: columns,
        data: data
    })

    const{ getTableProps, getTableBodyProps, headerGroups, rows, prepareRow} = tableInstance
    
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
    
    useEffect(() => {
        setPlayerProfiles_2(props.props.team2)
    }, [props]);


    const columns_2 = useMemo(() => COLUMNS_TEAMS_2, [])
    
    const tableInstance_2 = useTable({
        columns: columns_2,
        data: pProfiles_2
    })

    const{ getTableProps, getTableBodyProps, headerGroups, rows, prepareRow} = tableInstance_2
    
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
