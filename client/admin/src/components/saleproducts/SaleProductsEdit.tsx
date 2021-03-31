import React from 'react'
import { Edit, SimpleForm, TextInput, AutocompleteInput, ReferenceInput, SelectInput } from 'react-admin'
import { parse } from 'query-string';

const choices = [
    { id: 1, name: 'name', },
    { id: 2, name: 'name' }
];
const optionRenderer = choice => `${choice.id} ${choice.name}`;

const SaleProductsEdit = (props: any) => {
    const { saleId: saleId_string } = parse(props.location.search);
    const saleId = saleId_string ? parseInt(saleId_string, 10) : '';
    const redirect = saleId ? `api/v1/sales/${saleId}/products` : false;

    return (
        <Edit title='Nuevo SaleProduct' {...props}>
            <SimpleForm initialValues={{ saleId }} redirect={redirect}>
                <TextInput source='saleId' label='saleId' />
                <ReferenceInput label="Producto" source="productId" reference="api/v1/products">
                    <SelectInput choices={choices} optionText={optionRenderer} />
                </ReferenceInput>
                <TextInput source='size' />
                <TextInput source='quantity' />
                <AutocompleteInput source="paymentMethodId" choices={[
                    { id: '1', name: 'A CUENTA' },
                    { id: '2', name: 'EFECTIVO' },
                    { id: '3', name: 'MERCADO PAGO' },
                    { id: '4', name: 'TRANSFERENCIA' },
                ]} />
                <TextInput source='priceId' />
                <TextInput source='notes' />
            </SimpleForm>
        </Edit >
    )
}

export default SaleProductsEdit