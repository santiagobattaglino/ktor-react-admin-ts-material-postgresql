import React from 'react'
import { Create, SimpleForm, TextInput, AutocompleteInput } from 'react-admin'

const UserCreate = (props: any) => {
    return (
        <Create title='Nuevo Usuario' {...props}>
            <SimpleForm redirect="list">
                <AutocompleteInput source="role" choices={[
                    { id: 'PROVEEDOR', name: 'PROVEEDOR' },
                    { id: 'CLIENTE', name: 'CLIENTE' },
                    { id: 'ADMIN', name: 'ADMIN' },
                    { id: 'VENDEDOR', name: 'VENDEDOR' },
                ]} />
                <TextInput source='firstName' label='Nombre' autoFocus />
            </SimpleForm>
        </Create>
    )
}

export default UserCreate