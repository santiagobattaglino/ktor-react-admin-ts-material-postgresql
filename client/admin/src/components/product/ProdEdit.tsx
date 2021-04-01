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
                <ReferenceInput label="Color/Estampa" source="colorId" reference="api/v1/colors">
                    <SelectInput optionText="name" />
                </ReferenceInput>
                <TextInput source='idMl' label='idMl' />
                <TextInput source='manufacturingCost' label='Costo' />
                <TextInput source='photoId' label='Foto' />
                <TextInput source='notes' label='Notas' />
            </SimpleForm>
        </Edit>
    )
}

export default ProdEdit
