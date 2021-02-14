import React from 'react'
import { Edit, SimpleForm, TextInput, ReferenceInput, SelectInput } from 'react-admin'

const ProdEdit = (props: any) => {
    return (
        <Edit title='Editar Producto' {...props}>
            <SimpleForm  redirect="list">
                <ReferenceInput label="Categoría" source="catId" reference="api/v1/categories">
                    <SelectInput optionText="name" />
                </ReferenceInput>
                <TextInput source='name' label='Nombre' autoFocus />
                <TextInput source='material' label='Material' />
                <TextInput source='color' label='Color' />
                <TextInput source='idMl' label='idMl' />
                <ReferenceInput label="Precio" source="priceId" reference="api/v1/prices">
                    <SelectInput optionText="name" />
                </ReferenceInput>
                <TextInput source='notes' label='Notas' />
            </SimpleForm>
        </Edit>
    )
}

export default ProdEdit
