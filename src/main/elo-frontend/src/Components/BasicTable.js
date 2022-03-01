import React, {useState, useMemo, useEffect} from 'react'
import { useTable, useRowSelect } from 'react-table'
import {COLUMNS} from './Columns'
import axios from "axios";
import './table.css'
import Header from './Header';
import PostFormAddPlayer from './PostFormAddPlayer';
import {useNavigate} from "react-router-dom";


const obj = axios.get('http://localhost:8080/api/player/full_list');
console.log("hello");
var rowIDArray;
var objectID = {};



export const BasicTable = () => {
    
    //https://stackoverflow.com/questions/48980380/returning-data-from-axios-api
    //https://stackoverflow.com/questions/61925957/using-an-api-to-create-data-in-a-react-table Use this instead of usememo

    const [pProfiles, setPlayerProfiles] = useState([]);
    const [selectedProfiles, setSelectedProfiles] = useState([]);
    let navigate = useNavigate();

    obj.then(res => {setPlayerProfiles(res.data)});
    
    const columns = useMemo(() => COLUMNS, [])
    const data = pProfiles;

    const checked = (e) => {
        e.toggleRowSelected();
    }
    
    const handleSubmit = (e) => {
        // e.preventDefault();
        console.log(selectedProfiles);
        rowIDArray = selectedFlatRows.map(d => d.id);
        console.log(rowIDArray);
        objectID = {};
        rowIDArray.forEach(key => objectID[key] = true);
        console.log(objectID);
        // console.log(selectedProfiles.map(row => row.id));
        axios.post('http://localhost:8080/api/player/selectedPlayers', selectedProfiles);
        navigate("/listTeams")
    }

    const{ getTableProps, getTableBodyProps, headerGroups, rows, prepareRow, selectedFlatRows} = 
    useTable({
        columns: columns,
        data: data,
        initialState: {selectedRowIds: objectID}
    }, useRowSelect)
    
    useEffect(() => {
        setSelectedProfiles(selectedFlatRows.map((row) => row.original));
    }, [selectedFlatRows]);
      
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
                    //condition ? result_if_true : result_if_false
                    return (
                        <tr {...row.getRowProps()} onClick={() => checked(row)} className={row.isSelected ? 'row-selected' : 'row-not-selected'}>
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