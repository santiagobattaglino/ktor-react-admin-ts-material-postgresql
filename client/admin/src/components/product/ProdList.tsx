import React from 'react'
import { Datagrid, DeleteButton, EditButton, List, TextField, ReferenceField } from 'react-admin'

const ProdList = (props: any) => {
    return (
        <List {...props} perPage={25} pagination={false}>
            <Datagrid>
                <ReferenceField label="Categoría" source="catId" reference="api/v1/categories" sortBy="title">
                    <TextField source="name" />
                </ReferenceField>
                <TextField source='name' label='Nombre' />
                <TextField source='material' label='Material' />
                <TextField source='color' label='Color' />

                <EditButton basePath='products' undoable={true} />
                <DeleteButton basePath='products' undoable={true} />
            </Datagrid>
        </List>
    )
}

export default ProdList