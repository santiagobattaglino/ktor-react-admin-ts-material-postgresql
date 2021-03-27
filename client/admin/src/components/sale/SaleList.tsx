import React from 'react'
import { Datagrid, DeleteButton, EditButton, List, TextField, ReferenceField, DateField } from 'react-admin'

const SaleList = (props: any) => {
    return (
        <List title="Sales" {...props} perPage={25}>
            <Datagrid>
                <TextField source='id' label='id' />
                <ReferenceField label="Lugar de Venta / Vendedor" source="sellerId" reference="api/v1/users" sortBy="firstName">
                    <TextField source="firstName" />
                </ReferenceField>
                <ReferenceField label="Cliente" source="clientId" reference="api/v1/users" sortBy="firstName">
                    <TextField source="firstName" />
                </ReferenceField>

                <EditButton basePath='sales' undoable={true} />
                <DeleteButton basePath='sales' undoable={true} />
            </Datagrid>
        </List>
    )
}

export default SaleList