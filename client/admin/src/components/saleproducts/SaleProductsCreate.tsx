import React from 'react'
import { Create, SimpleForm, TextInput, ReferenceInput, SelectInput, AutocompleteInput } from 'react-admin'
import { parse } from 'query-string';

const productChoice = [
    { id: 1, name: 'name' }
];
const productRenderer = (productChoice: any) => `${productChoice.id} ${productChoice.name}`;

const SaleProductsCreate = (props: any) => {
    const { saleId: saleId_string } = parse(props.location.search);
    const saleId = saleId_string ? parseInt(saleId_string, 10) : '';
    const redirect = saleId ? `/api/v1/sales/${saleId}/products` : 'list';
    const initialValues = () => ({ saleId: saleId, paymentMethodId: 6, priceId: 10 });

    return (
        <Create title='Nuevo SaleProduct' {...props}>
            <SimpleForm initialValues={initialValues} redirect={redirect}>
                <ReferenceInput perPage={false} label="Venta" source="saleId" reference="api/v1/sales">
                    <SelectInput optionText="id" />
                </ReferenceInput>
                <ReferenceInput perPage={false} label="Producto" source="productId" reference="api/v1/products">
                    <AutocompleteInput choices={productChoice} optionText={productRenderer} />
                </ReferenceInput>
                <TextInput source='size' />
                <TextInput source='quantity' />
                <ReferenceInput label="Método de Pago" source="paymentMethodId" reference="api/v1/options/type/paymentMethod">
                    <SelectInput optionText="name" />
                </ReferenceInput>
                <ReferenceInput label="Precio" source="priceId" reference="api/v1/options/type/price">
                    <SelectInput optionText="name" />
                </ReferenceInput>
                <TextInput source='customPrice' />
                <TextInput source='notes' />
            </SimpleForm>
        </Create>
    )
}

export default SaleProductsCreate