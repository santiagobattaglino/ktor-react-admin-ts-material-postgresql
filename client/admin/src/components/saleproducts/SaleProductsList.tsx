import React from 'react'
import { Datagrid, DeleteButton, EditButton, List, TextField, DateField, ReferenceField } from 'react-admin'

const SaleProductsList = (props: any) => {
    return (
        <List title="SalesProducts" {...props} perPage={25}>
            <Datagrid>
                <TextField source='id' label='id' />
                <TextField source='saleId' label='saleId' />
                <ReferenceField label="Producto" source="productId" reference="api/v1/products" sortBy="name">
                    <TextField source="name" />
                </ReferenceField>
                <TextField source='size' />
                <TextField source='quantity' />
                <TextField source='paymentMethodId' />
                <TextField source='priceId' />
                <TextField source='notes' />
                <DateField source="dateCreated" />
                
                <EditButton basePath='saleproducts' undoable={true} />
                <DeleteButton basePath='saleproducts' undoable={true} />
            </Datagrid>
        </List>
    )
}

export default SaleProductsList