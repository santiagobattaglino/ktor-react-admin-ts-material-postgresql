import React from 'react'
import { Create, SimpleForm, TextInput } from 'react-admin'

const ColorCreate = (props: any) => {
    return (
        <Create title='Nuevo Color' {...props}>
            <SimpleForm redirect="list">
                <TextInput source='name' label='Nombre' autoFocus />
            </SimpleForm>
        </Create>
    )
}

export default ColorCreate