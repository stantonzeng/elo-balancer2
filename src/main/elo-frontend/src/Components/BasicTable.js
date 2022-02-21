import React, {useState, useMemo} from 'react'
import { useTable } from 'react-table'
import {COLUMNS} from './Columns'
import axios from "axios";
import './table.css'
import Header from './Header';
import PostFormAddPlayer from './PostFormAddPlayer';

const obj = axios.get('http://localhost:8080/api/player/full_list');

export const BasicTable = () => {
    
    //https://stackoverflow.com/questions/48980380/returning-data-from-axios-api
    //https://stackoverflow.com/questions/61925957/using-an-api-to-create-data-in-a-react-table Use this instead of usememo
    const [pProfiles, setPlayerProfiles] = useState([]);
    
    obj.then(res => {
        // console.log(res.data);
        setPlayerProfiles(res.data)
    });
    
    const columns = useMemo(() => COLUMNS, [])
    const data = pProfiles;
    
    const tableInstance = useTable({
        columns: columns,
        data: data
    })

    const{ getTableProps, getTableBodyProps, headerGroups, rows, prepareRow} = tableInstance
    
    return (
        <><Header />
        <div  className = "full-table-players">
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
        </div>
        <PostFormAddPlayer/></>

    )
}