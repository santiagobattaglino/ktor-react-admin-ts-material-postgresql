import React from 'react'
import { Datagrid, DeleteButton, EditButton, List, TextField } from 'react-admin'

const PriceList = (props: any) => {
    return (
        <List title="Precios" {...props} perPage={25} pagination={false}>
            <Datagrid>
                <TextField source='cloth_1_name' label='Nombre de la Tela 1' />
                <EditButton basePath='prices' undoable={false} />
                <DeleteButton basePath='prices' undoable={false} />
            </Datagrid>
        </List>
    )
}

export default PriceList