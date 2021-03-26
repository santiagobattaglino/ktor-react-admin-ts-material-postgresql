import React from 'react'
import {Create, SimpleForm, TextInput } from 'react-admin'

const SaleCreate = (props: any) => {
    return (
        <Create title='Nueva Venta' {...props}>
            <SimpleForm redirect="list">
                <TextInput source='placeId' />
                <TextInput source='userId' />
                <TextInput source='notes' />
            </SimpleForm>
        </Create>
    )
}

export default SaleCreate