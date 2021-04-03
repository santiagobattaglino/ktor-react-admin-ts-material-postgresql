import { useMediaQuery } from '@material-ui/core';
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
    Filter,
    SimpleList,
    ReferenceArrayField,
    SingleFieldList,
    ChipField
} from 'react-admin'

const ProdFilter = (props) => (
    <Filter {...props}>
        <TextInput label="Search" source="q" alwaysOn />
        <ReferenceInput label="Categoría" source="catId" reference="api/v1/categories" allowEmpty>
            <SelectInput optionText="name" />
        </ReferenceInput>
    </Filter>
);

const productRowStyle = (record, index) => ({
    backgroundColor: record.nb_views >= 500 ? '#efe' : 'white',
});

const catComponent = (rprops) => (
    <ReferenceArrayField
        label="Categoría"
        reference="api/v1/categories"
        source="catId">
        <SingleFieldList linkType="show">
            <ChipField source="name" />
        </SingleFieldList>
    </ReferenceArrayField>
);

const ProdList = (props: any) => {
    const isSmall = useMediaQuery((theme: any) => theme.breakpoints.down('sm'));
    return (
        <List title="Productos" filters={<ProdFilter />} {...props} perPage={25}>
            {isSmall ? (
                <SimpleList
                    primaryText={(record: any) => `${record.id} ${record.name}`}
                    secondaryText={(record: any) => {
                        <ReferenceField label="Categoría" source="catId" reference="api/v1/categories" sortBy="name" record={record}>
                            <TextField source="name" />
                        </ReferenceField>
                    }}
                    tertiaryText={(record: any) => catComponent}
                    linkType={(record: any) => record.canEdit ? "edit" : "show"}
                    rowStyle={productRowStyle}>
                </SimpleList>
            ) : (
                <Datagrid>
                    <TextField source='id' label='SKU' />
                    <FunctionField
                        source='photoId'
                        label="Foto"
                        render={(record: any) => {
                            if (record.photoId !== null) {
                                return (
                                    <img src={`https://lh3.google.com/u/0/d/${record.photoId}=w80-h80`} alt={record.name} />
                                );
                            } else {
                                return (
                                    <p>Sin Foto</p>
                                );
                            }
                        }}
                    />

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
            )}
        </List>
    )
}

export default ProdList