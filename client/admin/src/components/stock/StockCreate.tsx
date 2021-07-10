import React from 'react'
import { Create, SimpleForm, TextInput, ReferenceInput, SelectInput, required } from 'react-admin'

const choices = [
    { id: 1, name: 'name', },
    { id: 2, name: 'name' }
];
const optionRenderer = (choice: any) => `${choice.id} ${choice.name}`;

const StockCreate = (props: any) => {
    return (
        <Create title='Nuevo Movimiento de Stock' {...props}>
            <SimpleForm redirect="list">
                <ReferenceInput validate={required()} perPage={false} label="Producto" source="productId" reference="api/v1/products">
                    <SelectInput choices={choices} optionText={optionRenderer} />
                </ReferenceInput>
                <ReferenceInput validate={required()} label="Usuario" source="userId" reference="api/v1/users" sortBy="firstName">
                    <SelectInput optionText="firstName" />
                </ReferenceInput>
                <TextInput source='t1' />
                <TextInput source='t2' />
                <TextInput source='t3' />
                <TextInput source='t4' />
                <TextInput source='t5' />
                <TextInput source='t6' />
                <TextInput source='t7' />
                <TextInput source='t8' />
                <TextInput source='t9' />
                <TextInput source='t10' />
                <TextInput source='t11' />
            </SimpleForm>
        </Create>
    )
}

export default StockCreate