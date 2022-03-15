<ReactTable
    data={data}
    columns={columns}
    defaultPageSize={10}
    getTrProps={(state, rowInfo, column, instance, expanded) => {
    return {
        
    };
    }}
    SubComponent={row => {
    return (
        <div style={{ padding: "20px" }}>
        <em>
            You can put any component you want here, even another React
            Table!
        </em>
        <br />
        <br />
        <ReactTable
            data={data}
            columns={columns}
            defaultPageSize={3}
            showPagination={false}
            SubComponent={row => {
            return (
                <>
                    <ListTeams1 props={newData[0]} />
                    <p>Elo Difference: {newData[0].eloDifference}</p>
                    <ListTeams2 props={newData[0]} />
                </>
            );
            }}
        />
        </div>
    );
    }}
/>