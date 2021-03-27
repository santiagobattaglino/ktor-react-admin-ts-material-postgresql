import React from 'react'
import { Edit, SimpleForm, TextInput, ReferenceInput, SelectInput } from 'react-admin'

const SaleEdit = (props: any) => {
    return (
        <Edit title='Editar Venta' {...props}>
            <SimpleForm redirect="list">
                <ReferenceInput label="Lugar de Venta / Vendedor" source="sellerId" reference="api/v1/users">
                    <SelectInput optionText="firstName" />
                </ReferenceInput>
                <ReferenceInput label="Cliente" source="clientId" reference="api/v1/users">
                    <SelectInput optionText="firstName" />
                </ReferenceInput>
                <TextInput source='notes' />
            </SimpleForm>
        </Edit>
    )
}

export default SaleEdit
