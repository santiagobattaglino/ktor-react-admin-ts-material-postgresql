import React from 'react'
import { Create, SimpleForm, TextInput } from 'react-admin'
import { parse } from 'query-string';

const SaleProductsCreate = (props: any) => {
    const { saleId: saleId_string } = parse(props.location.search);
    const saleId = saleId_string ? parseInt(saleId_string, 10) : '';
    const redirect = saleId ? `api/v1/sales/${saleId}/products` : false;

    return (
        <Create title='Nuevo SaleProduct' {...props}>
            <SimpleForm initialValues={{ saleId }} redirect={redirect}>
                <TextInput source='saleId' label='saleId' />
                <TextInput source='productId' label='productId' />
                <TextInput source='size' />
                <TextInput source='quantity' />
                <TextInput source='paymentMethodId' />
                <TextInput source='priceId' />
                <TextInput source='notes' />
            </SimpleForm>
        </Create>
    )
}

export default SaleProductsCreate