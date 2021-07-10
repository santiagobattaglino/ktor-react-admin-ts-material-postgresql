import React from 'react'
import { Datagrid, List, TextField, ReferenceField, FunctionField } from 'react-admin'

const ReportList = (props: any) => {
    return (
        <List title="Disponibilidad de Productos" {...props} pagination={false}>
            <Datagrid>
                <ReferenceField label="Producto" source="productId" reference="api/v1/products" sortBy="name">
                    <FunctionField
                        label="Nombre"
                        render={(record: any) => `SKU ${record.id} - ${record.name}`}
                    />
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
            </Datagrid>
        </List>
    )
}

export default ReportList