import { required } from 'react-admin';
import { Create, SimpleForm, TextInput, ReferenceInput, SelectInput } from 'react-admin'

const SaleCreate = (props: any) => {
    const initialValues = () => ({ paymentMethodId: 6, priceId: 10 });
    return (
        <Create title='Nueva Venta' {...props}>
            <SimpleForm initialValues={initialValues} redirect="edit">
                <TextInput source='notes' validate={required()} />

                <ReferenceInput label="De Lugar de Venta / Vendedor" source="sellerId" reference="api/v1/users">
                    <SelectInput optionText="firstName" />
                </ReferenceInput>

                <ReferenceInput label="A Cliente" source="clientId" reference="api/v1/users">
                    <SelectInput optionText="firstName" />
                </ReferenceInput>

                <ReferenceInput label="Método de Pago" source="paymentMethodId" reference="api/v1/options/type/paymentMethod">
                    <SelectInput optionText="name" />
                </ReferenceInput>

                <ReferenceInput label="Precio" source="priceId" reference="api/v1/options/type/price">
                    <SelectInput optionText="name" />
                </ReferenceInput>
            </SimpleForm>
        </Create>
    )
}

export default SaleCreate