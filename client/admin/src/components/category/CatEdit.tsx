import React from 'react'
import { Edit, SimpleForm, TextInput } from 'react-admin'

const CatEdit = (props: any) => {
    return (
        <Edit title='Editar Categoría' {...props}>
            <SimpleForm>
                <TextInput source='name' autoFocus />
            </SimpleForm>
        </Edit>
    )
}

export default CatEdit
