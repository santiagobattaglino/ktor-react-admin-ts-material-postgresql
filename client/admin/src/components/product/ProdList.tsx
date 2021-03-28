import React from 'react'
import {
    Datagrid,
    DeleteButton,
    EditButton,
    List,
    TextField,
    ReferenceField,
    DateField,
    FunctionField,
    TextInput,
    ReferenceInput,
    SelectInput,
    Filter
} from 'react-admin'

const ProdFilter = (props) => (
    <Filter {...props}>
        <TextInput label="Search" source="q" alwaysOn />
        <ReferenceInput label="Categoría" source="catId" reference="api/v1/categories" allowEmpty>
            <SelectInput optionText="name" />
        </ReferenceInput>
    </Filter>
);

const ProdList = (props: any) => {
    return (
        <List title="Productos" filters={<ProdFilter />} {...props} perPage={25}>
            <Datagrid>
                <TextField source='id' label='SKU' />
                <ReferenceField label="Categoría" source="catId" reference="api/v1/categories" sortBy="name">
                    <TextField source="name" />
                </ReferenceField>
                <TextField source='name' label='Nombre' />
                <TextField source='material' label='Material' />
                <ReferenceField label="Color/Estampa" source="colorId" reference="api/v1/colors" sortBy="name">
                    <TextField source="name" />
                </ReferenceField>
                <TextField source='manufacturingCost' label='Costo' />
                <FunctionField source='manufacturingCost' label="X Mayor" render={
                    record => `${Math.round(record.manufacturingCost * 1.8)}`} />
                <FunctionField source='manufacturingCost' label="Precio Capilla" render={
                    record => `${Math.round((record.manufacturingCost * 1.8) * 1.3)}`} />
                <FunctionField source='manufacturingCost' label="X Menor" render={
                    record => `${Math.round((record.manufacturingCost * 1.8) * 2)}`} />

                <DateField source="dateCreated" />

                <EditButton basePath='products' undoable={true} />
                <DeleteButton basePath='products' undoable={true} />
            </Datagrid>
        </List>
    )
}

export default ProdList