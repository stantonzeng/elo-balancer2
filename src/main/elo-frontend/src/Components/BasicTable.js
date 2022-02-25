import React, {useState, useMemo} from 'react'
import { useTable } from 'react-table'
import {COLUMNS} from './Columns'
import axios from "axios";
import './table.css'
import Header from './Header';
import PostFormAddPlayer from './PostFormAddPlayer';
import {useNavigate} from "react-router-dom";

const obj = axios.get('http://localhost:8080/api/player/full_list');

export const BasicTable = () => {
    
    //https://stackoverflow.com/questions/48980380/returning-data-from-axios-api
    //https://stackoverflow.com/questions/61925957/using-an-api-to-create-data-in-a-react-table Use this instead of usememo
    const [pProfiles, setPlayerProfiles] = useState([]);
    const [selectedProfiles, setSelectedProfiles] = useState([]);

    obj.then(res => {setPlayerProfiles(res.data)});
    
    const columns = useMemo(() => COLUMNS, [])
    const data = pProfiles;

    const checker = (e) => {
        console.log(e);
        setSelectedProfiles(selectedProfiles => [...selectedProfiles, e]);
    }

    const handleSubmit = (e) => {
        // e.preventDefault();
        console.log(selectedProfiles);
        axios.post('http://localhost:8080/api/player/selectedPlayers', selectedProfiles);
        navigate("/listTeams")
    }
    

    const tableInstance = useTable({
        columns: columns,
        data: data
    })

    const{ getTableProps, getTableBodyProps, headerGroups, rows, prepareRow} = tableInstance
    
    let navigate = useNavigate();
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
                    //() => console.log(row.original)
                    // () => arrayPlayers.push(row.original)
                    return (
                        <tr {...row.getRowProps()} onClick={() => checker(row.original)}>
                            {row.cells.map((cell) => {
                                return <td {...cell.getCellProps()}>{cell.render('Cell')}</td>;
                            })}
                        </tr>
                    );
                })}
            </tbody>
        </table>
        </div>
        <PostFormAddPlayer/>
        <form onSubmit = {handleSubmit}>
            <button type = "button" onClick = {handleSubmit}>
                Click Me
            </button>
        </form>
        </>

    )
}