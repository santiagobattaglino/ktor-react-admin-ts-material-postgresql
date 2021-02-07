import React from 'react'
import { Datagrid, DeleteButton, EditButton, List, TextField } from 'react-admin'

const CatList = (props: any) => {
    return (
        <List {...props} perPage={25}>
            <Datagrid>
                <TextField source='name' label='Nombre' />
                <EditButton basePath='categories' undoable={false} />
                <DeleteButton basePath='categories' undoable={false} />
            </Datagrid>
        </List>
    )
}

export default CatList