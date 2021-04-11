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
    const initialValues = () => ({ saleId: saleId });

    return (
        <Create title='Nuevo SaleProduct' {...props}>
            <SimpleForm initialValues={initialValues} redirect={redirect}>
                <ReferenceInput perPage={false} label="Venta" source="saleId" reference="api/v1/sales">
                    <SelectInput optionText="notes" />
                </ReferenceInput>

                <ReferenceInput perPage={false} label="Producto" source="productId" reference="api/v1/products">
                    <AutocompleteInput choices={productChoice} optionText={productRenderer} />
                </ReferenceInput>

                <TextInput source='size' label='Talle' />
                <TextInput source='quantity' label='Cantidad' />
                <TextInput source='customPrice' label='Otro Precio' />
                <TextInput source='notes' label='Notas' />
            </SimpleForm>
        </Create>
    )
}

export default SaleProductsCreate