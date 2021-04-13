import React from 'react'
import { Datagrid, DeleteButton, EditButton, List, TextField, DateField, ReferenceField, FunctionField } from 'react-admin'

const SaleProductsList = (props: any) => {
    return (
        <List title="SalesProducts" {...props} perPage={25}>
            <Datagrid>
                <ReferenceField label="Foto" source="productId" reference="api/v1/products" sortBy="product_id">
                    <FunctionField
                        source='photoId'
                        label="Foto"
                        sortable={false}
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
                </ReferenceField>

                <ReferenceField label="Producto" source="productId" reference="api/v1/products">
                    <FunctionField
                        label="Nombre"
                        render={(record: any) => `SKU ${record.id} - ${record.name}`}
                    />
                </ReferenceField>

                <ReferenceField label="Venta" source="saleId" reference="api/v1/sales">
                    <TextField source='notes' />
                </ReferenceField>

                <TextField source='size' label='Talle' />
                <TextField source='quantity' label='Cantidad' />

                <TextField source='manufacturingCostTotal' label='$' />

                <TextField source='notes' label='Notas' />

                <EditButton basePath='saleproducts' undoable={true} />
                <DeleteButton basePath='saleproducts' undoable={true} />
            </Datagrid>
        </List>
    )
}

export default SaleProductsList