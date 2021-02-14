import React from 'react'
import { Datagrid, DeleteButton, EditButton, List, TextField } from 'react-admin'

const UserList = (props: any) => {
    return (
        <List {...props} perPage={25} pagination={false}>
            <Datagrid>
                <TextField source='role' label='Rol' />
                <EditButton basePath='users' undoable={true} />
                <DeleteButton basePath='users' undoable={true} />
            </Datagrid>
        </List>
    )
}

export default UserList