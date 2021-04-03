import React from 'react'
import { Create, SimpleForm, TextInput, AutocompleteInput } from 'react-admin'

const OptionCreate = (props: any) => {
    return (
        <Create title='Nueva Opción' {...props}>
            <SimpleForm redirect="list">
                <TextInput source='name' label='Nombre' autoFocus />
                <AutocompleteInput source="type" label='Tipo' choices={[
                    { id: 'rol', name: 'ROL' },
                    { id: 'paymentMethod', name: 'METODO DE PAGO' },
                    { id: 'price', name: 'PRECIO' }
                ]} />
            </SimpleForm>
        </Create>
    )
}

export default OptionCreate