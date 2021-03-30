import React from 'react'
import { Create, SimpleForm, TextInput, ReferenceInput, SelectInput } from 'react-admin'

const SaleCreate = (props: any) => {
    return (
        <Create title='Nueva Venta' {...props}>
            <SimpleForm redirect="edit">
                <ReferenceInput label="Lugar de Venta / Vendedor" source="sellerId" reference="api/v1/users">
                    <SelectInput optionText="firstName" />
                </ReferenceInput>
                <ReferenceInput label="Cliente" source="clientId" reference="api/v1/users">
                    <SelectInput optionText="firstName" />
                </ReferenceInput>
                <TextInput source='notes' />
            </SimpleForm>
        </Create>
    )
}

export default SaleCreate