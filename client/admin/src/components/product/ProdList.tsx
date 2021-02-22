import React from 'react'
import { Datagrid, DeleteButton, EditButton, List, TextField, ReferenceField } from 'react-admin'

const ProdList = (props: any) => {
    return (
        <List title="Productos" {...props} perPage={25} pagination={false}>
            <Datagrid>
                <ReferenceField label="Categoría" source="catId" reference="api/v1/categories" sortBy="name">
                    <TextField source="name" />
                </ReferenceField>
                <TextField source='name' label='Nombre' />
                <TextField source='material' label='Material' />
                <ReferenceField label="Color/Estampa" source="colorId" reference="api/v1/colors" sortBy="name">
                    <TextField source="name" />
                </ReferenceField>
                <TextField source='manufacturingCost' label='Costo de Fabricación' />

                <EditButton basePath='products' undoable={true} />
                <DeleteButton basePath='products' undoable={true} />
            </Datagrid>
        </List>
    )
}

export default ProdList