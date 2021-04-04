import React from 'react'
import { required, minLength } from 'react-admin';
import { Edit, SimpleForm, TextInput, ReferenceInput, SelectInput } from 'react-admin'

const validateName = [required(), minLength(2)];

const ProdEdit = (props: any) => {
    return (
        <Edit title='Editar Producto' {...props}>
            <SimpleForm redirect="list">
                <ReferenceInput label="Categoría" source="catId" reference="api/v1/categories">
                    <SelectInput optionText="name" validate={validateName} />
                </ReferenceInput>
                <TextInput source='name' label='Nombre' validate={required()} autoFocus />
                <TextInput source='manufacturingCost' validate={required()} label='Costo' />
                <TextInput source='material' label='Material' />
                <ReferenceInput label="Color/Estampa" source="colorId" reference="api/v1/colors">
                    <SelectInput optionText="name" />
                </ReferenceInput>
                <TextInput source='idMl' label='idMl' />
                <TextInput source='photoId' label='Foto' />
                <TextInput source='notes' label='Notas' />
            </SimpleForm>
        </Edit>
    )
}

export default ProdEdit
