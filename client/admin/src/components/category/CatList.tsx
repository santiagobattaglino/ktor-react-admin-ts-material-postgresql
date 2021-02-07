import React from 'react'
import {Datagrid, DeleteButton, EditButton, List, TextField, DateField} from 'react-admin'

const PostList = (props: any) => {
    return (
        <List {...props}>
            <Datagrid>
                <TextField source='id'/>
                <TextField source='name'/>
                <DateField source='dateCreated'/>
                <EditButton basePath='categories'/>
                <DeleteButton basePath='categories'/>
            </Datagrid>
        </List>
    )
}

export default PostList