import React from 'react'
import { Datagrid, DeleteButton, EditButton, List, TextField, ReferenceField } from 'react-admin'

const StockList = (props: any) => {
    return (
        <List title="Stock" {...props} perPage={25}>
            <Datagrid>
                <ReferenceField label="Producto" source="productId" reference="api/v1/products" sortBy="name">
                    <TextField source="name" />
                </ReferenceField>
                <TextField source='size' label='Talle' />
                <TextField source='quantity' label='Cantidad' />
                <ReferenceField label="Usuario" source="userId" reference="api/v1/users" sortBy="firstName">
                    <TextField source="firstName" />
                </ReferenceField>
                <TextField source='notes' label='Notes' />
                <EditButton basePath='stock' undoable={true} />
                <DeleteButton basePath='stock' undoable={true} />
            </Datagrid>
        </List>
    )
}

export default StockList