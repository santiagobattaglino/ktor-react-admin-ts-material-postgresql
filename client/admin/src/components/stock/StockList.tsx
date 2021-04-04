import React from 'react'
import { Filter, ReferenceInput, SelectInput } from 'react-admin';
import { Datagrid, DeleteButton, EditButton, List, TextField, ReferenceField, DateField, FunctionField } from 'react-admin'

const Filters = (props: any) => (
    <Filter {...props}>
        <ReferenceInput
            perPage={false}
            label="Producto"
            source="productId"
            reference="api/v1/products"
            allowEmpty
            alwaysOn
        >
            <SelectInput
                choises={[{ id: 1, name: 'name' }]}
                optionText={(choice: any) => `${choice.id} ${choice.name}`}
            />
        </ReferenceInput>
        <ReferenceInput
            perPage={false}
            label="Usuario"
            source="userId"
            reference="api/v1/users"
            allowEmpty
            alwaysOn
        >
            <SelectInput optionText="firstName" />
        </ReferenceInput>
    </Filter>
);

const StockList = (props: any) => {
    return (
        <List title="Stock" filters={<Filters />} {...props} perPage={25} sort={{ field: 'id', order: 'DESC' }}>
            <Datagrid>
                <TextField source='id' label='id' />
                <ReferenceField label="Producto" source="productId" reference="api/v1/products" sortBy="product_id">
                    <FunctionField
                        label="Nombre"
                        render={(record: any) => `SKU ${record.id} - ${record.name}`}
                    />
                </ReferenceField>

                <ReferenceField label="Usuario" source="userId" reference="api/v1/users" sortBy="user_id">
                    <TextField source="firstName" />
                </ReferenceField>
                <TextField sortable={false} source='t1' />
                <TextField sortable={false} source='t2' />
                <TextField sortable={false} source='t3' />
                <TextField sortable={false} source='t4' />
                <TextField sortable={false} source='t5' />
                <TextField sortable={false} source='t6' />
                <TextField sortable={false} source='t7' />
                <TextField sortable={false} source='t8' />
                <TextField sortable={false} source='t9' />
                <TextField sortable={false} source='t10' />
                <TextField sortable={false} source='t11' />
                <TextField sortable={false} source='notes' label='Notes' />
                <DateField source="dateCreated" />

                <EditButton basePath='stock' undoable={true} />
                <DeleteButton basePath='stock' undoable={true} />
            </Datagrid>
        </List>
    )
}

export default StockList