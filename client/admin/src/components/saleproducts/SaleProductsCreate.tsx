import React from 'react'
import { Create, SimpleForm, TextInput, ReferenceInput, SelectInput, AutocompleteInput } from 'react-admin'
import { parse } from 'query-string';

const choices = [
    { id: 1, name: 'name', },
    { id: 2, name: 'name' }
];
const optionRenderer = choice => `${choice.id} ${choice.name}`;

const SaleProductsCreate = (props: any) => {
    const { saleId: saleId_string } = parse(props.location.search);
    const saleId = saleId_string ? parseInt(saleId_string, 10) : '';
    const redirect = saleId ? `api/v1/sales/${saleId}/products` : false;

    return (
        <Create title='Nuevo SaleProduct' {...props}>
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
                <AutocompleteInput source="priceId" choices={[
                    { id: '1', name: 'X MAYOR' },
                    { id: '2', name: 'PRECIO CAPILLA' },
                    { id: '3', name: 'PRECIO POR MENOR' },
                    { id: '4', name: 'OTRO' },
                ]} />
                <TextInput source='customPrice' />

                <TextInput source='notes' />
            </SimpleForm>
        </Create>
    )
}

export default SaleProductsCreate