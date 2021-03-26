import React from 'react'
import { Edit, SimpleForm, TextInput } from 'react-admin'

const SaleEdit = (props: any) => {
    return (
        <Edit title='Editar Venta' {...props}>
            <SimpleForm redirect="list">
                <TextInput source='placeId' />
                <TextInput source='userId' />
                <TextInput source='notes' />
            </SimpleForm>
        </Edit>
    )
}

export default SaleEdit
