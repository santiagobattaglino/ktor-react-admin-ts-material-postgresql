import React from 'react'
import { Edit, SimpleForm, TextInput, AutocompleteInput } from 'react-admin'

const StockEdit = (props: any) => {
    return (
        <Edit title='Editar Producto' {...props}>
            <SimpleForm redirect="list">
                <TextInput source='firstName' label='Nombre' autoFocus />
                <AutocompleteInput source="role" choices={[
                    { id: 'PROVEEDOR', name: 'PROVEEDOR' },
                    { id: 'CLIENTE', name: 'CLIENTE' },
                    { id: 'ADMIN', name: 'ADMIN' },
                    { id: 'VENDEDOR', name: 'VENDEDOR' },
                ]} />
            </SimpleForm>
        </Edit>
    )
}

export default StockEdit
