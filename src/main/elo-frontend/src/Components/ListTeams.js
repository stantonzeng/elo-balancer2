import React, {useState, useMemo, useEffect} from 'react'
import { useTable} from 'react-table'
import {COLUMNS_TEAMS_1, COLUMNS_TEAMS_2} from './Columns'
import axios from "axios";
import './table.css';
import useSWR from 'swr'


// const useFetchData = () => {
//     const [data, setData] = useState({});
//     useEffect(() => {
//       const fetchData = async () => {
//           const { data: response } = await axios.get('http://localhost:8080/api/player/balanceTeams');
//           setData(response);
//       };
//       fetchData();
//         // axios.get('http://localhost:8080/api/player/balanceTeams').then(response => setData(response.data));
//     }, []);
    
//     return data;
//   };

export function ListTeamsBoth () {
    // const location = useLocation();
    // console.log("LOCATION FOR LSIT: ");
    // console.log(location);
    const fetcher = url => axios.get(url).then(res => res.data)
    setTimeout(() => {
        console.log("Waiting...");
    }, 100);
    const { data, error } = useSWR('http://localhost:8080/api/player/balanceTeams', fetcher)
    if (error) console.log("lol");
    if (!data) return <div>loading...</div>

    const newData = data;
    console.log(newData);
    console.log(newData[0]);
    return(
        <div>
            <ListTeams1 props = {newData}/><ListTeams2 props = {newData}/> 
        </div>
    )
}

export function ListTeams1 (props){
    
    
    const [pProfiles, setPlayerProfiles] = useState([]);
    const [elo1, setElo1] = useState();

    console.log(props.props);
    console.log(props.props[0]);

    useEffect(() => {
        setElo1(props.props[0].eloDifference);
        setPlayerProfiles(props.props[0].team1);
    }, [props]);
    
    
    const columns = useMemo(() => COLUMNS_TEAMS_1, [])
    const data = pProfiles;
    
    const tableInstance = useTable({
        columns: columns,
        data: data
    })

    const{ getTableProps, getTableBodyProps, headerGroups, rows, prepareRow} = tableInstance
    
    return (
        <><table {...getTableProps()}>
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
        <p>Elo Difference: {elo1}</p></>
    )
}

export function ListTeams2 (props) {
    const [pProfiles, setPlayerProfiles] = useState([]);
    
    useEffect(() => {
        setPlayerProfiles(props.props[0].team2)
    }, [props]);


    const columns = useMemo(() => COLUMNS_TEAMS_2, [])
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
