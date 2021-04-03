import React from 'react'
import { Datagrid, DeleteButton, EditButton, List, TextField } from 'react-admin'

const OptionList = (props: any) => {
    return (
        <List title="Opciones" {...props} perPage={25} pagination={false}>
            <Datagrid>
                <TextField source='name' label='Nombre' />
                <TextField source='type' label='Tipo' />
                <EditButton basePath='options' undoable={false} />
                <DeleteButton basePath='options' undoable={false} />
            </Datagrid>
        </List>
    )
}

export default OptionList