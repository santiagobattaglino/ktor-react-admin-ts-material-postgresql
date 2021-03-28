import React from 'react'
import { Datagrid, DeleteButton, EditButton, List, TextField, ReferenceField, DateField } from 'react-admin'

const StockList = (props: any) => {
    return (
        <List title="Stock" {...props} perPage={25}>
            <Datagrid>
                <TextField source='id' label='id' />
                <ReferenceField label="Producto" source="productId" reference="api/v1/products" sortBy="name">
                    <TextField source="name" />
                </ReferenceField>
                <ReferenceField label="Usuario" source="userId" reference="api/v1/users" sortBy="firstName">
                    <TextField source="firstName" />
                </ReferenceField>
                <TextField source='t1' />
                <TextField source='t2' />
                <TextField source='t3' />
                <TextField source='t4' />
                <TextField source='t5' />
                <TextField source='t6' />
                <TextField source='t7' />
                <TextField source='t8' />
                <TextField source='t9' />
                <TextField source='t10' />
                <TextField source='t11' />
                <TextField source='notes' label='Notes' />
                <DateField source="dateCreated" />

                <EditButton basePath='stock' undoable={true} />
                <DeleteButton basePath='stock' undoable={true} />
            </Datagrid>
        </List>
    )
}

export default StockList