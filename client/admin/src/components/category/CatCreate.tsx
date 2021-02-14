import React from 'react'
import { Create, SimpleForm, TextInput } from 'react-admin'

const CatCreate = (props: any) => {
    return (
        <Create title='Nueva Categoría' {...props}>
            <SimpleForm redirect="list">
                <TextInput source='name' label='Nombre' autoFocus />
            </SimpleForm>
        </Create>
    )
}

export default CatCreate