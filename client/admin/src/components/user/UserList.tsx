import React from 'react'
import { Datagrid, DeleteButton, EditButton, List, TextField } from 'react-admin'

const UserList = (props: any) => {
    return (
        <List title="Usuarios" {...props} perPage={25} pagination={false}>
            <Datagrid>
                <TextField source='firstName' label='Nombre' />
                <TextField source='role' label='Rol' />
                <EditButton basePath='users' undoable={true} />
                <DeleteButton basePath='users' undoable={true} />
            </Datagrid>
        </List>
    )
}

export default UserList