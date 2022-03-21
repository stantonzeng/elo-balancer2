import React, {useState, useMemo, useEffect } from 'react'
import { useTable, useRowSelect } from 'react-table'
import {COLUMNS} from './Columns'
import axios from "axios";
import './table.css'
import './button.css'
import Header from './Header';
import PostFormAddPlayer from './PostFormAddPlayer';
import useSWR from 'swr'
import {useNavigate} from "react-router-dom";
import { useSortBy } from 'react-table/dist/react-table.development';

var rowIDArray;
var objectID = {};


//https://getcssscan.com/css-buttons-examples
export function BasicTable(){

    const fetcher = url => axios.get(url).then(res => res.data)
    const [sleeping, setSleeping] = useState(true)


    const { data, error } = useSWR(sleeping ? null : 'http://localhost:8080/api/player/full_list', fetcher, {revalidateOnFocus: false});
    
    useEffect(() => {
        setTimeout(() => {
            setSleeping(false)
        }, 500)
    }, [])

    if (error) console.log("lol");
    if (!data) return <div className = "loading">loading...</div>

    return(
        <BasicTableTemp obj = {data}/>
    )

}

export function BasicTableTemp(obj){
    
    
    //https://stackoverflow.com/questions/48980380/returning-data-from-axios-api
    //https://stackoverflow.com/questions/61925957/using-an-api-to-create-data-in-a-react-table Use this instead of usememo

    const [pProfiles, setPlayerProfiles] = useState([]);
    const [selectedProfiles, setSelectedProfiles] = useState([]);
    const [allowButton, setAllowButton] = useState(false);
    let navigate = useNavigate();

    // listData.then(res => {setPlayerProfiles(res.data)});
    useEffect(() => {
        setPlayerProfiles(obj.obj);
        if(Object.keys(objectID).length < 4){
            setAllowButton(false);
        }
        else{
            setAllowButton(true);
        }
    }, [obj]);
    
    
    const columns = useMemo(() => COLUMNS, []);
    const data = pProfiles;

    const checked = (e) => {
        e.toggleRowSelected();
    }
    
    const handleSubmit = (e) => {
        // e.preventDefault();

        console.log(selectedProfiles);
        rowIDArray = selectedProfiles.map(d => d.id);
        console.log(rowIDArray);
        objectID = {};
        rowIDArray.forEach(key => objectID[key-1] = true);
        console.log(objectID);
        axios.post('http://localhost:8080/api/player/selectedPlayers', selectedProfiles);
        // window.location = "/listTeams";
        navigate("/listTeams")
    }

    const{ getTableProps, getTableBodyProps, headerGroups, rows, prepareRow, selectedFlatRows} = 
    useTable({
        columns: columns,
        data: data,
        initialState: {
            selectedRowIds: objectID
        }
    }, useSortBy, useRowSelect)
    
    useEffect(() => {
        setSelectedProfiles(selectedFlatRows.map((row) => row.original));
        console.log(selectedFlatRows.length);
        if(selectedFlatRows.length < 4){
            setAllowButton(false);
        }
        else{
            setAllowButton(true);
        }
    }, [selectedFlatRows]);
      
    return (
        <><Header />
        <form onSubmit = {handleSubmit}>
            <button className = {allowButton ? "button" : "invalid-button"} type = 'submit' disabled={!allowButton} onClick = {handleSubmit}>
                <span className="text">
                    Balance Teams
                </span>
            </button>
        </form>
        <div className = "table-and-button">
            <div  className = "full-table-players">
            <table {...getTableProps()}>
                <thead>
                    {headerGroups.map((headerGroup) => (
                        <tr {...headerGroup.getHeaderGroupProps()}>
                            {headerGroup.headers.map((column) => (
                                <th {...column.getHeaderProps(column.getSortByToggleProps())}>{column.render('Header')}
                                </th>
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
        </div>
        
        
                
        <PostFormAddPlayer/>
        
        </>
    )
}