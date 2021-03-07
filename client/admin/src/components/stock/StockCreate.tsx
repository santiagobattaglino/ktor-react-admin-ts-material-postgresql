import React from 'react'
import {AutocompleteInput, Create, SimpleForm, TextInput} from 'react-admin'

const StockCreate = (props: any) => {
    return (
        <Create title='Nuevo Usuario' {...props}>
            <SimpleForm redirect="list">
                <TextInput source='firstName' label='Nombre' autoFocus/>
                <AutocompleteInput source="role" choices={[
                    {id: 'PROVEEDOR', name: 'PROVEEDOR'},
                    {id: 'CLIENTE', name: 'CLIENTE'},
                    {id: 'ADMIN', name: 'ADMIN'},
                    {id: 'VENDEDOR', name: 'VENDEDOR'},
                ]}/>
            </SimpleForm>
        </Create>
    )
}

export default StockCreate