import React from 'react'
import { Datagrid, DeleteButton, EditButton, List, TextField } from 'react-admin'

const ColorList = (props: any) => {
    return (
        <List title="Colores" {...props} perPage={25}>
            <Datagrid>
                <TextField source='name' label='Nombre' />
                <EditButton basePath='colors' undoable={false} />
                <DeleteButton basePath='colors' undoable={false} />
            </Datagrid>
        </List>
    )
}

export default ColorList