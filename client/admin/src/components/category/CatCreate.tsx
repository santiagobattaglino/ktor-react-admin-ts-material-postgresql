import React from 'react'
import { Create, SimpleForm, TextInput } from 'react-admin'

const CatCreate = (props: any) => {
    return (
        <Create title='Nueva Categoría' {...props}>
            <SimpleForm>
                <TextInput source='name'/>
            </SimpleForm>
        </Create>
    )
}

export default CatCreate