import { Datagrid, DeleteButton, EditButton, List, TextField, ReferenceField, DateField } from 'react-admin'

const SaleList = (props: any) => {
    return (
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

                <DateField source="dateCreated" label='Fecha' />

                <EditButton basePath='sales' undoable={true} />
                <DeleteButton basePath='sales' undoable={true} />
            </Datagrid>
        </List>
    )
}

export default SaleList