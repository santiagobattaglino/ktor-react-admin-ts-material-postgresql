import React from 'react';
import { Loading, Error } from 'react-admin';
import { useQuery } from 'react-admin';
import { Datagrid, DeleteButton, EditButton, List, TextField, ReferenceField, DateField, FunctionField } from 'react-admin'

const Total = () => {
    const { data, total, loading, error } = useQuery({
        type: 'getList',
        resource: 'api/v1/sales',
        payload: { pagination: { page: 1, perPage: 25 }, sort: { field: 'id', order: 'DESC' } }
    });

    if (loading) return <Loading />;
    if (error) return <Error />;
    console.log(data)

    if (!data || data.length === 0) return null;

    const sum = data.map(sales => sales.total).reduce((a, b) => a + b)
    console.log(sum)

    return (
        <ul>
            <li>Total: {sum}</li>
        </ul>
    );
};

const SaleList = (props: any) => {
    return (
        <div>
            <List title="Sales" {...props} perPage={25} sort={{ field: 'id', order: 'DESC' }}>
                <Datagrid>
                    <TextField source='notes' label='Notas' />

                    <ReferenceField label="De Lugar de Venta / Vendedor" source="sellerId" reference="api/v1/users" sortBy="firstName">
                        <TextField source="firstName" />
                    </ReferenceField>

                    <ReferenceField label="A Cliente" source="clientId" reference="api/v1/users" sortBy="firstName">
                        <TextField source="firstName" />
                    </ReferenceField>

                    <ReferenceField label="Método de Pago" source="paymentMethodId" reference="api/v1/options/type/paymentMethod">
                        <TextField source="name" />
                    </ReferenceField>

                    <ReferenceField label="Precio" source="priceId" reference="api/v1/options/type/price">
                        <TextField source="name" />
                    </ReferenceField>

                    <TextField source="total" />
                    <DateField source="dateCreated" label='Fecha' />

                    <EditButton basePath='sales' undoable={true} label='Editar / Agregar Productos' />
                    <DeleteButton basePath='sales' undoable={true} label='Eliminar' />
                </Datagrid>
            </List>
            <Total />
        </div>

    )
}

/*
<FunctionField
    source='manufacturingCostTotal'
    label="$"
    render={(record: any) => {
        if (record.priceId === 9) {
            return `${Math.round(record.total * 1.7)}`
        } else if (record.priceId === 10) {
            return `${Math.round((record.total * 1.8) * 1.2)}`
        } else if (record.priceId === 11) {
            return `${Math.round((record.total * 1.8) * 2)}`
        } else if (record.priceId === 12) {
            return `${Math.round(((record.total * 1.8) * 2) + ((record.total * 1.8) * 2) * 0.12)}`
        } else {
            return `${0}`
        }
    }}
/>
*/
export default SaleList