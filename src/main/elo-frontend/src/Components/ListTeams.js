import React, {useState, useMemo} from 'react'
import { useTable} from 'react-table'
import {COLUMNS_TEAMS_1, COLUMNS_TEAMS_2} from './Columns'
import axios from "axios";
import './table.css'

const obj = axios.get('http://localhost:8080/api/player/balanceTeams/10');
obj.then(res => console.log(res.data[0]));

export const ListTeams1 = () => {
    const [pProfiles, setPlayerProfiles] = useState([]);
    const [elo1, setElo1] = useState([]);
    obj.then(res => {
        setElo1(res.data[0].eloDifference);
        setPlayerProfiles(res.data[0].team1)
    });
    
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
                <tr>
                    <td></td>
                </tr>
            </tbody>
        </table>
        <p>Elo Difference: {elo1}</p></>
    )
}

export const ListTeams2 = () => {
    const [pProfiles, setPlayerProfiles] = useState([]);
    
    obj.then(res => {
        // console.log(res.data);
        setPlayerProfiles(res.data[0].team2)
    });
    
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
                <tr>
                    <td></td>
                </tr>
            </tbody>
        </table>
    )
}