import React from 'react'
import { Create, SimpleForm, TextInput } from 'react-admin'

const PriceCreate = (props: any) => {
    return (
        <Create title='Nuevo Precio' {...props}>
            <SimpleForm redirect="list">
                <TextInput source='cloth_1_name' label='Nombre de la Tela 1' autoFocus />
                <TextInput source='cloth_1_amount' label='Cantidad de Tela 1 (m2)' />
                <TextInput source='cloth_2_name' label='Nombre de la Tela 2' />
                <TextInput source='cloth_2_amount' label='Cantidad de Tela 2 (m2)' />
                <TextInput source='elastEmb' label='elastEmb (m)' />
                <TextInput source='elastCintura' label='elastCintura (m)' />
                <TextInput source='elastBajoB' label='elastBajoB (m)' />
                <TextInput source='puntilla' label='puntilla (m)' />
                <TextInput source='bretel' label='bretel (m)' />
                <TextInput source='argollas' label='argollas (unidades)' />
                <TextInput source='ganchos' label='ganchos (unidades)' />
                <TextInput source='reguladores' label='reguladores (unidades)' />
                <TextInput source='manoDeObra' label='Costo de mano de obra ($)' />
            </SimpleForm>
        </Create>
    )
}

export default PriceCreate