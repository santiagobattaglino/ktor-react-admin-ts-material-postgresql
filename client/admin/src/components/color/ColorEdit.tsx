import React from 'react'
import { Edit, SimpleForm, TextInput } from 'react-admin'

const ColorEdit = (props: any) => {
    return (
        <Edit title='Editar Color' {...props}>
            <SimpleForm>
                <TextInput source='name' label='Nombre' autoFocus />
            </SimpleForm>
        </Edit>
    )
}

export default ColorEdit
