import React from 'react'
import { required, minLength, maxLength, email, minValue, regex, choices, number } from 'react-admin';
import { Create, SimpleForm, TextInput, ReferenceInput, SelectInput } from 'react-admin'

// https://marmelab.com/react-admin/CreateEdit.html#validation
const validateName = [required(), minLength(2), maxLength(15)];
const emailMsg = ({ translate }) => translate('myroot.validation.email_invalid');
const validateEmail = email(emailMsg);
const validateAge = [number(), minValue(18)];
const validateZipCode = regex(/^\d{5}$/, 'Must be a valid Zip Code');
const validateSex = choices(['m', 'f'], 'Must be Male or Female');

const ProdCreate = (props: any) => {
    return (
        <Create title='Nuevo Producto' {...props}>
            <SimpleForm redirect="list">
                <ReferenceInput label="Categoría" source="catId" reference="api/v1/categories">
                    <SelectInput optionText="name" validate={validateName} />
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
        </Create>
    )
}

export default ProdCreate