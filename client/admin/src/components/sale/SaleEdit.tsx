import { required } from 'react-admin';
import {
    Edit,
    TextInput,
    ReferenceInput,
    SelectInput,
    ReferenceManyField,
    Datagrid,
    TextField,
    EditButton,
    ReferenceField,
    FunctionField,
    TabbedForm,
    FormTab
} from 'react-admin'
import AddProductsButton from './AddProductsButton';

const SaleEdit = (props: any) => {
    return (
        <Edit title='Editar Venta' {...props}>
            <TabbedForm redirect="edit">
                    <FormTab label="Summary">
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
                    </FormTab>

                    <FormTab label="Productos" path="products">
                        <ReferenceManyField
                            addLabel={false}
                            reference="api/v1/saleproducts"
                            target="saleId"
                            sort={{ field: 'id', order: 'DESC' }}  >
                            <Datagrid>
                                <ReferenceField label="Producto" source="productId" reference="api/v1/products" sortBy="name">
                                    <FunctionField
                                        label="Nombre"
                                        render={(record: any) => `SKU ${record.id} - ${record.name}`}
                                    />
                                </ReferenceField>
                                <TextField source='size' label='Talle' />
                                <TextField source='quantity' label='Cantidad' />
                                <TextField source='notes' label='Notas' />
                                <EditButton />
                            </Datagrid>
                        </ReferenceManyField>
                        <br />
                        <AddProductsButton {...props} />
                    </FormTab>
                
            </TabbedForm>
        </Edit>
    )
}

export default SaleEdit
