import React from 'react'
import { Datagrid, DeleteButton, EditButton, List, TextField, ReferenceField, DateField } from 'react-admin'

const SaleList = (props: any) => {
    return (
        <List title="Sales" {...props} perPage={25}>
            <Datagrid>
                <TextField source='id' label='id' />
                <TextField source='placeId' label='Lugar de venta' />
                <TextField source='userId' label='Usuario' />

                <EditButton basePath='sales' undoable={true} />
                <DeleteButton basePath='sales' undoable={true} />
            </Datagrid>
        </List>
    )
}

export default SaleList