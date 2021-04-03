import React from 'react'
import { Edit, SimpleForm, TextInput, ReferenceInput, SelectInput, TextField } from 'react-admin'
import { parse } from 'query-string';

const productChoice = [
    { id: 1, name: 'name', },
    { id: 2, name: 'name' }
];
const productRenderer = (productChoice: any) => `${productChoice.id} ${productChoice.name}`;

const optionChoice = [
    { id: 1, type: 'type', name: 'name', },
    { id: 2, type: 'type', name: 'name' }
];
const optionRenderer = (optionChoice: any) => `${optionChoice.name} - ${optionChoice.type}`;

const SaleProductsEdit = (props: any) => {
    const { saleId: saleId_string } = parse(props.location.search);
    const saleId = saleId_string ? parseInt(saleId_string, 10) : '';
    const redirect = saleId ? `api/v1/sales/${saleId}/products` : 'list';

    return (
        <Edit title='Nuevo SaleProduct' {...props}>
            <SimpleForm initialValues={{ saleId }} redirect={redirect}>
                <ReferenceInput label="Producto" source="productId" reference="api/v1/products">
                    <SelectInput choices={productChoice} optionText={productRenderer} />
                </ReferenceInput>
                <TextInput source='size' />
                <TextInput source='quantity' />
                <ReferenceInput label="Método de Pago" source="paymentMethodId" reference="api/v1/options">
                    <SelectInput choices={optionChoice} optionText={optionRenderer} fullWidth />
                </ReferenceInput>
                <ReferenceInput label="Precio" source="priceId" reference="api/v1/options">
                    <SelectInput choices={optionChoice} optionText={optionRenderer} fullWidth />
                </ReferenceInput>
                <TextInput source='customPrice' />
                <TextInput source='notes' />
            </SimpleForm>
        </Edit >
    )
}

export default SaleProductsEdit