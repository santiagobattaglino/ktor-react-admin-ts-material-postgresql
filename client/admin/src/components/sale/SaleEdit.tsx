import React from 'react'
import {
    Edit,
    SimpleForm,
    TextInput,
    ReferenceInput,
    SelectInput,
    Tab,
    TabbedShowLayout,
    ReferenceManyField,
    Datagrid,
    DateField,
    TextField,
    ShowButton
} from 'react-admin'
import AddProductsButton from './AddProductsButton';

const SaleEdit = (props: any) => {
    return (
        <Edit title='Editar Venta' {...props}>
            <TabbedShowLayout>
                <Tab label="Summary">
                    <SimpleForm redirect="list">
                        <ReferenceInput label="Lugar de Venta / Vendedor" source="sellerId" reference="api/v1/users">
                            <SelectInput optionText="firstName" />
                        </ReferenceInput>
                        <ReferenceInput label="Cliente" source="clientId" reference="api/v1/users">
                            <SelectInput optionText="firstName" />
                        </ReferenceInput>
                        <TextInput source='notes' />
                    </SimpleForm>
                </Tab>
                <Tab label="Productos" path="products">
                    <ReferenceManyField
                        addLabel={false}
                        reference="api/v1/saleproducts"
                        target="saleId"
                        sort={{ field: 'id', order: 'DESC' }}  >
                        <Datagrid>
                            <TextField source='id' label='id' />
                            <TextField source='saleId' label='saleId' />
                            <TextField source='productId' label='productId' />
                            <TextField source='size' />
                            <TextField source='quantity' />
                            <TextField source='paymentMethodId' />
                            <TextField source='priceId' />
                            <TextField source='notes' />
                            <DateField source="dateCreated" />
                            <ShowButton />
                        </Datagrid>
                    </ReferenceManyField>
                    <AddProductsButton {...props} />
                </Tab>
            </TabbedShowLayout>
        </Edit>
    )
}

export default SaleEdit
