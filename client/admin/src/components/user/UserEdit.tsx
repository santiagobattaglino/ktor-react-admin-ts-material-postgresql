import React from 'react'
import { Edit, SimpleForm, TextInput, AutocompleteInput } from 'react-admin'

const UserEdit = (props: any) => {
    return (
        <Edit title='Editar Producto' {...props}>
            <SimpleForm redirect="list">
                <AutocompleteInput source="role" choices={[
                    { id: 'PROVEEDOR', name: 'PROVEEDOR' },
                    { id: 'CLIENTE', name: 'CLIENTE' },
                    { id: 'ADMIN', name: 'ADMIN' },
                    { id: 'VENDEDOR', name: 'VENDEDOR' },
                ]} />
                <TextInput source='firstName' label='Nombre' autoFocus />
            </SimpleForm>
        </Edit>
    )
}

export default UserEdit
