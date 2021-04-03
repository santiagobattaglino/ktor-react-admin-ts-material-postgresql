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
    EditButton,
    ReferenceField,
    FunctionField
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
                            <ReferenceField label="Producto" source="productId" reference="api/v1/products" sortBy="name">
                                <FunctionField
                                    label="Nombre"
                                    render={(record: any) => `SKU ${record.id} - ${record.name}`}
                                />
                            </ReferenceField>
                            <TextField source='size' label='Talle' />
                            <TextField source='quantity' label='Cantidad' />
                            <TextField source='notes' label='Notas' />
                            <DateField source="dateCreated" label='Fecha' />
                            <EditButton />
                        </Datagrid>
                    </ReferenceManyField>
                    <AddProductsButton {...props} />
                </Tab>
            </TabbedShowLayout>
        </Edit>
    )
}

export default SaleEdit
