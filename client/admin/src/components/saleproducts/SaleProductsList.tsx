import React from 'react'
import { Datagrid, DeleteButton, EditButton, List, TextField, DateField, ReferenceField, FunctionField } from 'react-admin'

const SaleProductsList = (props: any) => {
    return (
        <List title="SalesProducts" {...props} perPage={25}>
            <Datagrid>
                <ReferenceField label="Producto" source="productId" reference="api/v1/products" sortBy="name">
                    <FunctionField
                        label="Nombre"
                        render={(record: any) => `SKU ${record.id} - ${record.name}`}
                    />
                </ReferenceField>
                <TextField source='size' />
                <TextField source='quantity' />
                <TextField source='notes' />
                <DateField source="dateCreated" />
                
                <EditButton basePath='saleproducts' undoable={true} />
                <DeleteButton basePath='saleproducts' undoable={true} />
            </Datagrid>
        </List>
    )
}

export default SaleProductsList