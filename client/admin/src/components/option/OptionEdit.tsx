import React from 'react'
import { Edit, SimpleForm, TextInput, AutocompleteInput } from 'react-admin'

const OptionEdit = (props: any) => {
    return (
        <Edit title='Editar Opción' {...props}>
            <SimpleForm>
                <TextInput source='name' label='Nombre' autoFocus />
                <AutocompleteInput source="type" label='Tipo' choices={[
                    { id: 'rol', name: 'ROL' },
                    { id: 'paymentMethod', name: 'METODO DE PAGO' },
                    { id: 'price', name: 'PRECIO' }
                ]} />
            </SimpleForm>
        </Edit>
    )
}

export default OptionEdit
